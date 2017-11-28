package me.dbarnett.dozealert

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import com.jjoe64.graphview.series.DataPoint
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*
import java.nio.file.Files.exists
import android.media.ToneGenerator
import android.media.AudioManager
import me.dbarnett.dozealert.dt.core.DecisionTree
import me.dbarnett.dozealert.dt.core.DecisionTreeLearner
import me.dbarnett.dozealert.dt.core.Example
import me.dbarnett.dozealert.dt.util.ArraySet
import java.io.IOException
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.os.Vibrator


/**
 * Created by daniel on 11/9/17.
 */
class DataProcessing(val context: Context) {

    private val scaleVolts = 1200.0 / (8388607.0 * 1.5 * 51.0)
    var sensitivity = 1.00
    var alarmLength = 0
    var learner: DecisionTreeLearner? = null
    var tree: DecisionTree? = null
    val problem = DrowsinessTester()
    var channel1Queue: LinkedList<Double> = LinkedList()
    var channel2Queue: LinkedList<Double> = LinkedList()
    var fft: FastFourierTransform = FastFourierTransform(256)
    val channel1FFTPrevMag = DoubleArray(256)
    val channel2FFTPrevMag = DoubleArray(256)
    var shouldUpdate = 0
    var sleepyCount = 0
    val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
    var v: Vibrator? = null
    var prefs: SharedPreferences? = null



    private fun convert19bitToInt32(a: Int, b: Int, c: Int): Int {
        val threeByteBuffer = intArrayOf(a, b, c)
        var prefix = 0
        return if((threeByteBuffer[2] and 0x01) > 0){
            prefix = 0b1111111111111
            (prefix shl 19) or (threeByteBuffer[0] shl 16) or (threeByteBuffer[1] shl 8) or threeByteBuffer[2] or 0xFFFFFFFF.toInt().inv()
        }else {
            (prefix shl 19) or (threeByteBuffer[0] shl 16) or (threeByteBuffer[1] shl 8) or threeByteBuffer[2]
        }
    }

    fun writeData(data: String, strFilePath: String) {

        val csvWriter: PrintWriter
        try {

            var file = File(context.filesDir.path.toString(), strFilePath)
            if (!file.exists()) {
                file = File(context.filesDir.path.toString() + strFilePath)
            }
            csvWriter = PrintWriter(FileWriter(file, true))


            csvWriter.print(data)
            csvWriter.append('\n')


            csvWriter.close()


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun processData(buffer: IntArray, isTraining: Boolean, eyesOpen: Boolean) {



        val receivedDeltas = Array(2) { DoubleArray(4) }
        if(100 < (buffer[0]) && (buffer[0]) < 201){
            receivedDeltas[0][0] = convert19bitToInt32(
                    buffer[1] shr 5,
                    (((buffer[1] and 0x1F) shl 3) and 0xFF) or (buffer[2] shr 5),
                    (((buffer[2] and 0x1F) shl 3) and 0xFF) or (buffer[3] shr 5)) * scaleVolts

            receivedDeltas[0][1] = convert19bitToInt32(
                    (buffer[3] and 0x1F) shr 2,
                    (buffer[3] shl 6 and 0xFF) or (buffer[4] shr 2),
                    (buffer[4] shl 6 and 0xFF) or (buffer[5] shr 2)) * scaleVolts

            receivedDeltas[0][2] = convert19bitToInt32(
                    ((buffer[5] and 0x03) shl 1 and 0xFF) or (buffer[6] shr 7),
                    ((buffer[6] and 0x7F) shl 1 and 0xFF) or (buffer[7] shr 7),
                    ((buffer[7] and 0x7F) shl 1 and 0xFF) or (buffer[8] shr 7)) * scaleVolts

            receivedDeltas[0][3] = convert19bitToInt32(
                    (buffer[8] and 0x7F) shr 4,
                    ((buffer[8] and 0x0F) shl 4 and 0xFF) or (buffer[9] shr 4),
                    ((buffer[9] and 0x0F) shl 4 and 0xFF) or (buffer[10] shr 4)) * scaleVolts

            receivedDeltas[1][0] = convert19bitToInt32(
                    (buffer[10] and 0x0F) shr 1,
                    (buffer[10] shl 7 and 0xFF) or (buffer[11] shr 1),
                    (buffer[11] shl 7 and 0xFF) or (buffer[12] shr 1)) * scaleVolts

            receivedDeltas[1][1] = convert19bitToInt32(
                    ((buffer[12] and 0x01) shl 2 and 0xFF) or (buffer[13] shr 6),
                    (buffer[13] shl 2 and 0xFF) or (buffer[14] shr 6),
                    (buffer[14] shl 2 and 0xFF) or (buffer[15] shr 6)) * scaleVolts

            receivedDeltas[1][2] = convert19bitToInt32(
                    (buffer[15] and 0x38) shr 3,
                    ((buffer[15] and 0x07) shl 5 and 0xFF) or ((buffer[16] and 0xF8) shr 3),
                    ((buffer[16] and 0x07) shl 5 and 0xFF) or ((buffer[17] and 0xF8) shr 3)) * scaleVolts

            receivedDeltas[1][3] = convert19bitToInt32((buffer[17] and 0x07), buffer[18], buffer[19]) * scaleVolts


            channel1Queue.addLast(receivedDeltas[0][0])
            channel1Queue.addLast(receivedDeltas[1][0])

            channel2Queue.addLast(receivedDeltas[0][1])
            channel2Queue.addLast(receivedDeltas[1][1])

            if (channel1Queue.size > 256){

                channel1Queue.removeFirst()
                channel2Queue.removeFirst()
                if (channel1Queue.size > 256){
                    channel1Queue.removeFirst()
                    channel2Queue.removeFirst()
                }

                shouldUpdate += 1
                if (shouldUpdate > 3) {
                    shouldUpdate = 0

                    val channel1FFTReal = DoubleArray(256)
                    val channel1FFTImag = DoubleArray(256)
                    val channel2FFTReal = DoubleArray(256)
                    val channel2FFTImag = DoubleArray(256)
                    for (i in 0..255){
                        channel1FFTReal[i] = channel1Queue[i]
                        channel2FFTReal[i] = channel2Queue[i]
                    }


                    fft.fft(channel1FFTReal, channel1FFTImag)
                    fft.fft(channel2FFTReal, channel2FFTImag)
                    val channel1FFTMag = DoubleArray(256)
                    val channel2FFTMag = DoubleArray(256)

                    val channel1SmoothMag = DoubleArray(256)
                    val channel2SmoothMag = DoubleArray(256)
                    val channelAverage = DoubleArray(256)



                    for (i in 0..255) {
                        channel1FFTMag[i] = Math.sqrt(Math.pow(channel1FFTReal[i], 2.0) + Math.pow(channel1FFTImag[i], 2.0))

                        channel2FFTMag[i] = Math.sqrt(Math.pow(channel2FFTReal[i], 2.0) + Math.pow(channel2FFTImag[i], 2.0))


                        channel1SmoothMag[i] = (channel1FFTMag[i] + channel1FFTPrevMag[i])/2
                        channel2SmoothMag[i] = (channel2FFTMag[i] + channel2FFTPrevMag[i])/2
                        channel1FFTPrevMag[i] = channel1SmoothMag[i]
                        channel2FFTPrevMag[i] = channel2SmoothMag[i]
                        channelAverage[i] = (channel1FFTMag[i] + channel2FFTMag[i])/2
                    }
                    var alpha = 0.0
                    var beta = 0.0
                    var delta = 0.0
                    var theta = 0.0
                    var gamma = 0.0

                    var alphaNum = 0
                    var betaNum = 0
                    var deltaNum = 0
                    var thetaNum = 0
                    var gammaNum = 0

                    for (i in 0..255){
                        val index = index2Freq(i, 256, 200)
                        when {
                            index < 0.5 -> {
                                //do nothing
                            }
                            index < 4.0 -> {
                                delta += channelAverage[i]
                                deltaNum += 1
                            }
                            index < 8.0 -> {
                                theta += channelAverage[i]
                                thetaNum += 1
                            }
                            index < 13.0 -> {
                                alpha += channelAverage[i]
                                alphaNum += 1
                            }
                            index < 30.0 -> {
                                beta += channelAverage[i]
                                betaNum += 1
                            }
                            index < 55.0 -> {
                                gamma += channelAverage[i]
                                gammaNum += 1
                            }
                        }

                    }

                    alpha /= alphaNum
                    beta /= betaNum
                    delta /= deltaNum
                    theta /= thetaNum
                    gamma /= gammaNum


                    var ratio = (alpha + theta)/beta

                    println(ratio)

                    var alphaInput = ""
                    var betaInput = ""
                    var deltaInput = ""
                    var thetaInput = ""

                    when {
                        alpha < .25 -> {
                            alphaInput = "0"
                        }
                        alpha < .5 -> {
                            alphaInput = "1"
                        }
                        alpha < .75 -> {
                            alphaInput = "2"
                        }
                        alpha < 1 -> {
                            alphaInput = "3"
                        }
                        alpha < 1.25 -> {
                            alphaInput = "4"
                        }
                        alpha < 1.5 -> {
                            alphaInput = "5"
                        }
                        alpha < 1.75 -> {
                            alphaInput = "6"
                        }
                        else -> {
                            alphaInput = "7"
                        }
                    }

                    when {
                        beta < .2 -> {
                            betaInput = "0"
                        }
                        beta < .4 -> {
                            betaInput = "1"
                        }
                        beta < .6 -> {
                            betaInput = "2"
                        }
                        beta < .8 -> {
                            betaInput = "3"
                        }
                        beta < 1 -> {
                            betaInput = "4"
                        }
                        beta < 1.2 -> {
                            betaInput = "5"
                        }
                        beta < 1.4 -> {
                            betaInput = "6"
                        }
                        else -> {
                            betaInput = "7"
                        }
                    }

                    when {
                        delta < .2 -> {
                            deltaInput = "0"
                        }
                        delta < .4 -> {
                            deltaInput = "1"
                        }
                        delta < .6 -> {
                            deltaInput = "2"
                        }
                        delta < .8 -> {
                            deltaInput = "3"
                        }
                        delta < 1 -> {
                            deltaInput = "4"
                        }
                        delta < 1.2 -> {
                            deltaInput = "5"
                        }
                        delta < 1.4 -> {
                            deltaInput = "6"
                        }
                        else -> {
                            deltaInput = "7"
                        }
                    }

                    when {
                        theta < .2 -> {
                            thetaInput = "0"
                        }
                        theta < .4 -> {
                            thetaInput = "1"
                        }
                        theta < .6 -> {
                            thetaInput = "2"
                        }
                        theta < .8 -> {
                            thetaInput = "3"
                        }
                        theta < 1 -> {
                            thetaInput = "4"
                        }
                        theta < 1.2 -> {
                            thetaInput = "5"
                        }
                        theta < 1.4 -> {
                            thetaInput = "6"
                        }
                        else -> {
                            thetaInput = "7"
                        }
                    }

                    val dataString = "$alphaInput,$betaInput,$deltaInput,$thetaInput,$eyesOpen"

                    if(isTraining) {

                        writeData(dataString, "braindata.csv")
                    }else{
                        val example = Example()
                        example.setInputValue(problem.inputs[0], alphaInput)
                        example.setInputValue(problem.inputs[1], betaInput)
                        example.setInputValue(problem.inputs[2], deltaInput)
                        example.setInputValue(problem.inputs[3], thetaInput)
                        //example.outputValue = eyesOpen.toString()
                        if(true) {


                            if(ratio < 1.15 && ratio > .8 ){
                                sleepyCount += 1

                                if (sleepyCount >= 20){
                                    if(alarmLength > 0) {
                                        if (prefs!!.getBoolean("alarm_switch", true)) {
                                            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, alarmLength)
                                        }
                                        if (prefs!!.getBoolean("vibration_switch", true)) {
                                            v!!.vibrate(alarmLength.toLong())
                                        }
                                    }
                                    alarmLength += 25

                                    sleepyCount = 15
                                }

                            }else{
                                //println("Not Sleepy")
                                alarmLength = 0
                                sleepyCount = 0
                            }
                        }

                    }

                }

            }

        }
    }

    @Throws(IOException::class)
    fun buildTester() {
        v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        problem.dump()
        var file = File(context.filesDir.path.toString(), "braindata.csv")
        if (!file.exists()) {
            file = File(context.filesDir.path.toString() + "braindata.csv")


            println(file.exists())
            if (file.exists()) {
                val examples = problem.readExamplesFromCSVFile(file)
                val training = ArraySet<Example>()
                println(examples.toString())

                val validation = ArraySet<Example>()

                var odd = 0
                for (e in examples) {
                    if (odd == 0) {
                        training.add(e)
                        odd = 1
                    } else if (odd == 1) {
                        validation.add(e)
                        odd = 0
                    }
                }
                learner = DecisionTreeLearner(problem)
                tree = learner!!.learn(examples)
                tree!!.dump()
                println(tree!!.validate(validation))
            }

        }
    }

    fun index2Freq(i: Int, samples: Int, nFFT: Int): Double {
        return i * (samples / nFFT / 2.0)
    }
}