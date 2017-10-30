/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
* This file is a modified version of https://github.com/googlesamples/android-BluetoothLeGatt
* This Activity is started by the DeviceScanActivity and is passed a device name and address to start with
* Clicking on the listed device, opens up a page which allows connection and for scanning of GATT Services available for the device
* */
//package com.example.android.dozealert;
package me.dbarnett.dozealert

import android.app.Activity
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.DataPointInterface
import com.jjoe64.graphview.series.LineGraphSeries
import java.lang.reflect.Array
import java.math.BigInteger
import java.util.*

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with `BluetoothLeService`, which in turn interacts with the
 * Bluetooth LE API.
 */
class DeviceControlActivity : Activity() {
    private var mConnectionState: TextView? = null
    private var mDataField: TextView? = null
    private var mDeviceName: String? = null
    private var mDeviceAddress: String? = null
    private var mGattServicesList: ExpandableListView? = null
    private var mBluetoothLeService: BluetoothLeService? = null
    private var mGattCharacteristics: ArrayList<ArrayList<BluetoothGattCharacteristic>>? = ArrayList()
    private var mConnected = false
    private var mNotifyOnRead: BluetoothGattCharacteristic? = null
    var graph: GraphView? = null
    var channel1 = LineGraphSeries<DataPointInterface>()
    var channel2 = LineGraphSeries<DataPointInterface>()
    private var mIsDeviceGanglion: Boolean = false
    private var mIsDeviceCyton: Boolean = false
    private val mIsUnknownCharacteristic: Boolean = false
    var amount = 0.0
    var tStart = System.currentTimeMillis()
    private var isStreaming = false
    private val LIST_NAME = "NAME"
    private val LIST_UUID = "UUID"
    var channel1Queue: LinkedList<Complex> = LinkedList()
    var channel2Queue: LinkedList<Complex> = LinkedList()

    // Code to manage Service lifecycle.
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            mBluetoothLeService = (service as BluetoothLeService.LocalBinder).service
            if (!mBluetoothLeService!!.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth")
                finish()
            }
            // Automatically connects to the device upon successful start-up initialization.
            Log.v(TAG, "Trying to connect to GATTServer on: $mDeviceName Address: $mDeviceAddress")
            mBluetoothLeService!!.connect(mDeviceAddress)
            mCommandIdx = 0

        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothLeService.ACTION_GATT_CONNECTED == action) {
                Log.v(TAG, "GattServer Connected")
                mConnected = true
                updateConnectionState(R.string.connected)
                invalidateOptionsMenu()
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED == action) {
                Log.v(TAG, "GattServer Disconnected")
                mConnected = false
                updateConnectionState(R.string.disconnected)
                invalidateOptionsMenu()
                clearUI()
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED == action) {
                Log.v(TAG, "GattServer Services Discovered")
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService!!.supportedGattServices)
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE == action) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA))
            }
        }
    }

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private val servicesListClickListner = ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
        if (mGattCharacteristics != null) {
            //if it is a characteristic related view item, get the characteristic
            val characteristic = mGattCharacteristics!![groupPosition][childPosition]
            val charaProp = characteristic.properties

            if (mIsDeviceGanglion) {
                if (SampleGattAttributes.UUID_GANGLION_SEND == characteristic.uuid.toString()) {
                    //we use this only when the device is a ganglion
                    val c = toggleDataStream(characteristic)
                    Toast.makeText(applicationContext, "Sent: '$c' to Ganglion", Toast.LENGTH_SHORT).show()
                }

                if (SampleGattAttributes.UUID_GANGLION_RECEIVE == characteristic.uuid.toString()) {
                    //check if we have registered for notifications
                    val updateNotifyOnRead = setCharacteristicNotification(mNotifyOnRead, characteristic, "Ganglion RECEIVE")
                    if (updateNotifyOnRead) mNotifyOnRead = characteristic

                    //also read it for just now
                    mBluetoothLeService!!.readCharacteristic(characteristic)
                }

                if (SampleGattAttributes.UUID_GANGLION_DISCONNECT == characteristic.uuid.toString()) {
                    Log.v(TAG, "Not sure what the disconnect characteristic does")
                    Toast.makeText(applicationContext, "Disconnect Not Actionable", Toast.LENGTH_SHORT).show()
                }
                return@OnChildClickListener true//all done, return
            }

            if (mIsDeviceCyton) {
                if (SampleGattAttributes.UUID_CYTON_SEND == characteristic.uuid.toString()) {
                    //we use this only when the device is a ganglion
                    val c = toggleDataStream(characteristic)
                    Toast.makeText(applicationContext, "Sent: '$c' to Cyton", Toast.LENGTH_SHORT).show()
                }

                if (SampleGattAttributes.UUID_CYTON_RECEIVE == characteristic.uuid.toString()) {
                    //check if we have registered for notifications
                    val updateNotifyOnRead = setCharacteristicNotification(mNotifyOnRead, characteristic, "Cyton RECEIVE")
                    if (updateNotifyOnRead) mNotifyOnRead = characteristic

                    //also read it for just now
                    mBluetoothLeService!!.readCharacteristic(characteristic)
                }

                if (SampleGattAttributes.UUID_CYTON_DISCONNECT == characteristic.uuid.toString()) {
                    Log.v(TAG, "Not sure what the disconnect characteristic does")
                    Toast.makeText(applicationContext, "Disconnect Not Actionable", Toast.LENGTH_SHORT).show()
                }
                return@OnChildClickListener true//all done, return
            }


            //If here, this is either not a Cyton/Ganglion board OR not the 3 primary characteristics

            //specific readable characteristics

            //sample test using battery level service (PlayStore:BLE Peripheral Simulator) BATTERY_LEVEL characteristic notifies
            if (SampleGattAttributes.UUID_BATTERY_LEVEL == characteristic.uuid.toString()) {//the
                //we set it to notify, if it isn't already on notify
                Log.v(TAG, "Battery level notification registration")
                val updateNotifyOnRead = setCharacteristicNotification(mNotifyOnRead, characteristic, "Battery Level")
                if (updateNotifyOnRead) mNotifyOnRead = characteristic
                mBluetoothLeService!!.readCharacteristic(characteristic)
                return@OnChildClickListener true//all done, return
            }

            if (charaProp or BluetoothGattCharacteristic.PROPERTY_READ > 0) {
                //read it immediately
                Log.v(TAG, "Reading characteristic: " + characteristic.uuid.toString())
                mBluetoothLeService!!.readCharacteristic(characteristic)
            }

            if (charaProp or BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                val updateNotifyOnRead = setCharacteristicNotification(mNotifyOnRead, characteristic, SampleGattAttributes.getShortUUID(characteristic.uuid))
                if (updateNotifyOnRead) mNotifyOnRead = characteristic
                mBluetoothLeService!!.readCharacteristic(characteristic)
            }
            return@OnChildClickListener true
        }
        false
    }

    private fun toggleDataStream(BLEGChar: BluetoothGattCharacteristic): Char {
        if(!isStreaming){
            isStreaming = true
            tStart = System.currentTimeMillis()

        }else{
            isStreaming = false
        }
        val cmd = mCommands[mCommandIdx].toChar()
        Log.v(TAG, "Sending Command : " + cmd)
        BLEGChar.value = byteArrayOf(cmd.toByte())
        mBluetoothLeService!!.writeCharacteristic(BLEGChar)
        mCommandIdx = (mCommandIdx + 1) % mCommands.size //update for next run to toggle off
        return cmd
    }

    private fun setCharacteristicNotification(currentNotify: BluetoothGattCharacteristic?, newNotify: BluetoothGattCharacteristic, toastMsg: String): Boolean {
        if (currentNotify == null) {//none registered previously
            mBluetoothLeService!!.setCharacteristicNotification(newNotify, true)
        } else {//something was registered previously
            if (currentNotify.uuid != newNotify.uuid) {//we are subscribed to another characteristic?
                mBluetoothLeService!!.setCharacteristicNotification(currentNotify, false)//unsubscribe
                mBluetoothLeService!!.setCharacteristicNotification(newNotify, true) //subscribe to Receive
            } else {
                //no change required
                return false
            }
        }
        Toast.makeText(applicationContext, "Notify: " + toastMsg, Toast.LENGTH_SHORT).show()
        return true//indicates reassignment needed for mNotifyOnRead
    }

    private fun clearUI() {
        mGattServicesList!!.setAdapter(null as SimpleExpandableListAdapter?)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gatt_services_characteristics)

        //this activity was started by another with data stored in an intent, process it
        val intent = intent

        //get the devcie name and address
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME)
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS)

        //set flags if CYTON or GANGLION is being used
        Log.v(TAG, "deviceName '$mDeviceName'")
        if (mDeviceName != null) {
            mIsDeviceCyton = mDeviceName!!.toUpperCase().contains(SampleGattAttributes.DEVICE_NAME_CYTON)
            mIsDeviceGanglion = mDeviceName!!.toUpperCase().contains(SampleGattAttributes.DEVICE_NAME_GANGLION)
        } else {//device name is not available
            mIsDeviceGanglion = false
            mIsDeviceCyton = false
        }

        if (mIsDeviceCyton || mIsDeviceGanglion) {//if it is a desirable device
            Toast.makeText(applicationContext, "OpenBCI " + (if (mIsDeviceCyton) "Cyton" else "Ganglion") + " Connected", Toast.LENGTH_SHORT).show()
        }

        // Sets up UI references.
        (findViewById<View>(R.id.device_address) as TextView).text = mDeviceAddress
        mGattServicesList = findViewById<View>(R.id.gatt_services_list) as ExpandableListView
        mGattServicesList!!.setOnChildClickListener(servicesListClickListner)
        mConnectionState = findViewById<View>(R.id.connection_state) as TextView

        actionBar!!.title = mDeviceName
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)

        Log.v(TAG, "Creating Service to Handle all further BLE Interactions")
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)

        graph = findViewById<View>(R.id.graph) as GraphView
        channel1.color = Color.GREEN
        channel1.thickness = 2
        channel2.thickness = 2
        graph!!.addSeries(channel1)
        graph!!.addSeries(channel2)
        graph!!.viewport.setMaxY(20.0)
        graph!!.viewport.setMinY(0.0)
        graph!!.viewport.setMaxX(60.0)
        graph!!.viewport.setMinX(0.0)
        graph!!.viewport.isXAxisBoundsManual = true
        graph!!.viewport.isYAxisBoundsManual = true




    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
        if (mBluetoothLeService != null) {

            Log.v(TAG, "Trying to connect to: $mDeviceName Address: $mDeviceAddress")
            val result = mBluetoothLeService!!.connect(mDeviceAddress)
            Log.d(TAG, "Connect request result=" + result)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mGattUpdateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConnection)
        mBluetoothLeService = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gatt_services, menu)
        if (mConnected) {
            menu.findItem(R.id.menu_connect).isVisible = false
            menu.findItem(R.id.menu_disconnect).isVisible = true
        } else {
            menu.findItem(R.id.menu_connect).isVisible = true
            menu.findItem(R.id.menu_disconnect).isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_connect -> {
                //the 'connect' button is clicked and this triggers the connection request
                Log.v(TAG, "Trying to connect to: $mDeviceName Address: $mDeviceAddress on click")
                mBluetoothLeService!!.connect(mDeviceAddress)
                //the completion of the connection is returned separately
                return true
            }
            R.id.menu_disconnect -> {
                mBluetoothLeService!!.disconnect()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateConnectionState(resourceId: Int) {
        runOnUiThread { mConnectionState!!.setText(resourceId) }
    }

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

    private fun displayData(data: String?) {



        val scaleVolts = 1200 / (8388607.0 * 1.5 * 51.0)
        if (data != null) {
            val bufferStrings = data.split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            val buffer = bufferStrings.map { it.toInt() }.toIntArray()

            val receivedDeltas = Array(2) { DoubleArray(4) }
            if(100 < (buffer[0] and 0xFF) && (buffer[0] and 0xFF) < 201){
                receivedDeltas[0][0] = convert19bitToInt32((buffer[1] shr 5),
                        (((buffer[1] and 0x1F) shl 3) and 0xFF) or (buffer[2] shr 5),
                        (((buffer[2] and 0x1F) shl 3) and 0xFF) or (buffer[3] shr 5)) * scaleVolts

                receivedDeltas[0][1] = convert19bitToInt32((buffer[3] and 0x1F) shr 2,
                        (buffer[3] shl 6 and 0xFF) or (buffer[4] shr 2),
                        (buffer[4] shl 6 and 0xFF) or (buffer[5] shr 2)) * scaleVolts

                receivedDeltas[0][2] = convert19bitToInt32( ((buffer[5] and 0x03) shl 1 and 0xFF) or (buffer[6] shr 7),
                        ((buffer[6] and 0x7F) shl 1 and 0xFF) or (buffer[7] shr 7),
                        ((buffer[7] and 0x7F) shl 1 and 0xFF) or (buffer[8] shr 7)) * scaleVolts

                receivedDeltas[0][3] = convert19bitToInt32(((buffer[8] and 0x7F) shr 4),
                        ((buffer[8] and 0x0F) shl 4 and 0xFF) or (buffer[9] shr 4),
                        ((buffer[9] and 0x0F) shl 4 and 0xFF) or (buffer[10] shr 4)) * scaleVolts

                receivedDeltas[1][0] = convert19bitToInt32(((buffer[10] and 0x0F) shr 1),
                        (buffer[10] shl 7 and 0xFF) or (buffer[11] shr 1),
                        (buffer[11] shl 7 and 0xFF) or (buffer[12] shr 1)) * scaleVolts

                receivedDeltas[1][1] = convert19bitToInt32(((buffer[12] and 0x01) shl 2 and 0xFF) or (buffer[13] shr 6),
                        (buffer[13] shl 2 and 0xFF) or (buffer[14] shr 6),
                        (buffer[14] shl 2 and 0xFF) or (buffer[15] shr 6)) * scaleVolts

                receivedDeltas[1][2] = convert19bitToInt32(((buffer[15] and 0x38) shr 3),
                        ((buffer[15] and 0x07) shl 5 and 0xFF) or ((buffer[16] and 0xF8) shr 3),
                        ((buffer[16] and 0x07) shl 5 and 0xFF) or ((buffer[17] and 0xF8) shr 3)) * scaleVolts

                receivedDeltas[1][3] = convert19bitToInt32((buffer[17] and 0x07), buffer[18], buffer[19]) * scaleVolts


                channel1Queue.addFirst(Complex(receivedDeltas[0][0], 0.0))
                channel1Queue.addFirst(Complex(receivedDeltas[1][0], 0.0))

                channel2Queue.addFirst(Complex(receivedDeltas[0][1], 0.0))
                channel2Queue.addFirst(Complex(receivedDeltas[1][1], 0.0))

                if (channel1Queue.size > 256){
                    if (channel1Queue.size > 256){
                        channel1Queue.removeLast()
                        channel2Queue.removeLast()
                    }
                    channel1Queue.removeLast()
                    val channel1FFT = arrayOfNulls<Complex>(256)
                    for (i in 0..255){
                        channel1FFT[i] = channel1Queue[i]
                    }
                    FastFourierTransform.fft(channel1FFT)
                    val channel1DataPoints = arrayOfNulls<DataPoint>(256)
                    for (i in 0..255){

                        channel1DataPoints[i] = DataPoint(i.toDouble(), channel1FFT[i]!!.re)
                    }

                    channel1.resetData(channel1DataPoints)




                    channel2Queue.removeLast()
                    val channel2FFT = arrayOfNulls<Complex>(256)
                    for (i in 0..255){
                        channel2FFT[i] = channel2Queue[i]
                    }
                    FastFourierTransform.fft(channel2FFT)
                    val channel2DataPoints = arrayOfNulls<DataPoint>(256)
                    for (i in 0..255){

                        channel2DataPoints[i] = DataPoint(i.toDouble(), channel2FFT[i]!!.re)
                    }
                    channel2.resetData(channel2DataPoints)



                }




            }







        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private fun displayGattServices(gattServices: List<BluetoothGattService>?) {
        if (gattServices == null) return
        var uuid: String? = null
        val unknownServiceString = resources.getString(R.string.unknown_service)
        val unknownCharaString = resources.getString(R.string.unknown_characteristic)
        val gattServiceData = ArrayList<HashMap<String, String>>()
        val gattCharacteristicData = ArrayList<ArrayList<HashMap<String, String>>>()
        mGattCharacteristics = ArrayList()

        // Loops through available GATT Services.
        for (gattService in gattServices) {
            Log.w(TAG, "Service Iterator:" + gattService.uuid)

            if (mIsDeviceGanglion) {////we only want the SIMBLEE SERVICE, rest, we junk...
                if (SampleGattAttributes.UUID_GANGLION_SERVICE != gattService.uuid.toString()) continue
            }

            if (mIsDeviceCyton) {////we only want the RFDuino SERVICE, rest, we junk...
                if (SampleGattAttributes.UUID_CYTON_SERVICE != gattService.uuid.toString()) continue
            }

            val currentServiceData = HashMap<String, String>()
            uuid = gattService.uuid.toString()
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString))
            currentServiceData.put(LIST_UUID, uuid)
            gattServiceData.add(currentServiceData)

            val gattCharacteristicGroupData = ArrayList<HashMap<String, String>>()
            val gattCharacteristics = gattService.characteristics
            val charas = ArrayList<BluetoothGattCharacteristic>()

            // Loops through available Characteristics.
            for (gattCharacteristic in gattCharacteristics) {
                charas.add(gattCharacteristic)
                val currentCharaData = HashMap<String, String>()
                uuid = gattCharacteristic.uuid.toString()
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString))
                currentCharaData.put(LIST_UUID, uuid)
                gattCharacteristicGroupData.add(currentCharaData)

                //if this is the read attribute for Cyton/Ganglion, register for notify service
                if (SampleGattAttributes.UUID_GANGLION_RECEIVE == uuid || SampleGattAttributes.UUID_CYTON_RECEIVE == uuid) {//the RECEIVE characteristic
                    Log.v(TAG, "Registering notify for: " + uuid)
                    //we set it to notify, if it isn't already on notify
                    if (mNotifyOnRead == null) {
                        mBluetoothLeService!!.setCharacteristicNotification(gattCharacteristic, true)
                        mNotifyOnRead = gattCharacteristic
                    } else {
                        Log.v(TAG, "De-registering Notification for: " + mNotifyOnRead!!.uuid.toString() + " first")
                        mBluetoothLeService!!.setCharacteristicNotification(mNotifyOnRead!!, false)
                        mBluetoothLeService!!.setCharacteristicNotification(gattCharacteristic, true)
                        mNotifyOnRead = gattCharacteristic
                    }
                }
            }
            mGattCharacteristics!!.add(charas)
            gattCharacteristicData.add(gattCharacteristicGroupData)
        }

        val gattServiceAdapter = SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                arrayOf(LIST_NAME, LIST_UUID),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                arrayOf(LIST_NAME, LIST_UUID),
                intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        mGattServicesList!!.setAdapter(gattServiceAdapter)
    }

    companion object {
        private val TAG = "dozealert/" + DeviceControlActivity::class.java!!.getSimpleName()

        val EXTRAS_DEVICE_NAME = "DEVICE_NAME"
        val EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS"
        private val mCommands = byteArrayOf('b'.toByte(), 's'.toByte())
        private var mCommandIdx = 0

        private fun makeGattUpdateIntentFilter(): IntentFilter {
            val intentFilter = IntentFilter()
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED)
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED)
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)
            intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE)
            return intentFilter
        }
    }
}
