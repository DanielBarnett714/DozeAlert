package me.dbarnett.dozealert

import android.content.Context
import com.jjoe64.graphview.series.DataPoint
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*
import java.nio.file.Files.exists
import android.media.ToneGenerator
import android.media.AudioManager





/**
 * Created by daniel on 11/9/17.
 */
class DataProcessing(val context: Context) {

    private val scaleVolts = 1200.0 / (8388607.0 * 1.5 * 51.0)
    var sensitivity = 1.00
    var tStart = System.currentTimeMillis()
    private var isStreaming = false
    var channel1Queue: LinkedList<Double> = LinkedList()
    var channel2Queue: LinkedList<Double> = LinkedList()
    var fft: FastFourierTransform = FastFourierTransform(256)
    val channel1FFTPrevMag = DoubleArray(256)
    val channel2FFTPrevMag = DoubleArray(256)
    var channel1PrevData = 0.0
    var channel2PrevData = 0.0
    var drowsinessLevel = 0.0
    var shouldUpdate = 0
    var sleepyCount = 0
    val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)

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

    fun processData(buffer: IntArray, isTraining: Boolean): Double {



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
                    if (shouldUpdate > 10) {

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
                        channel1FFTMag[i] = Math.pow(channel1FFTReal[i], 2.0) + Math.pow(channel1FFTImag[i], 2.0)

                        channel2FFTMag[i] = Math.pow(channel2FFTReal[i], 2.0) + Math.pow(channel2FFTImag[i], 2.0)


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

                    var alphaNum = 0
                    var betaNum = 0
                    var deltaNum = 0
                    var thetaNum = 0

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
                            index < 7 -> {
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
                        }

                    }

                        alpha /= alphaNum
                        beta /= betaNum
                        delta /= deltaNum
                        theta /= thetaNum



                    if(isTraining) {
                        val dataString: String = alpha.toInt().toString() + "," + beta.toInt().toString() + "," + delta.toInt().toString() + "," + theta.toInt().toString()
                        //println(dataString)
                        writeData(dataString, "braindata.csv")
                    }
                    val ratio1 = beta  / (alpha + theta)
                    val ratio2 = (alpha + theta) / beta
                        if(ratio1 <= sensitivity){
                            println("Sleepy")
                            sleepyCount += 1

                            if (sleepyCount >= 5){

                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100)
                                sleepyCount = 3
                            }
                        }else{
                            println("Not Sleepy")
                            sleepyCount = 0
                        }

                        drowsinessLevel = ratio2 - ratio1
                        shouldUpdate = 0
                    }
                    return drowsinessLevel




                }




            }
        return 0.0
    }

    fun index2Freq(i: Int, samples: Int, nFFT: Int): Double {
        return i * (samples / nFFT / 2.0)
    }
}