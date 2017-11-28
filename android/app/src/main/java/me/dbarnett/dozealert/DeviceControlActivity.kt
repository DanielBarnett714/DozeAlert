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
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import java.util.*
import android.widget.SeekBar
import android.widget.Toast
import java.io.File


/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with `BluetoothLeService`, which in turn interacts with the
 * Bluetooth LE API.
 */
class DeviceControlActivity : Activity() {
    private var mStartButton: Button? = null
    private var mDeviceName: String? = null
    private var mDeviceAddress: String? = null
    private var mBluetoothLeService: BluetoothLeService? = null
    private var mGattCharacteristics: ArrayList<ArrayList<BluetoothGattCharacteristic>>? = ArrayList()
    private var mConnected = false
    private var mNotifyOnRead: BluetoothGattCharacteristic? = null

    private var mIsDeviceGanglion: Boolean = false
    private var mIsDeviceCyton: Boolean = false
    var simpleSeekBar: SeekBar? = null
    private val LIST_NAME = "NAME"
    private val LIST_UUID = "UUID"
    var isStreaming = false


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
                invalidateOptionsMenu()
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED == action) {
                Log.v(TAG, "GattServer Disconnected")
                mConnected = false
                invalidateOptionsMenu()
                clearUI()
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED == action) {
                Log.v(TAG, "GattServer Services Discovered")
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService!!.supportedGattServices)
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE == action) {
            }
        }
    }

    private fun toggleTraining(){
        val characteristic = mGattCharacteristics!![0][1]
        //we use this only when the device is a ganglion
        toggleDataStream(characteristic)

    }

    private fun toggleDataStream(BLEGChar: BluetoothGattCharacteristic): Char {
        val cmd = mCommands[mCommandIdx].toChar()
        Log.v(TAG, "Sending Command : " + cmd)
        BLEGChar.value = byteArrayOf(cmd.toByte())
        mBluetoothLeService!!.writeCharacteristic(BLEGChar)
        mCommandIdx = (mCommandIdx + 1) % mCommands.size //update for next run to toggle off
        return cmd
    }


    private fun clearUI() {
        mStartButton!!.isEnabled = false
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


        actionBar!!.title = "Connecting"
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)

        Log.v(TAG, "Creating Service to Handle all further BLE Interactions")
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)

        mStartButton = findViewById(R.id.button1)
        mStartButton!!.setOnClickListener {
            mBluetoothLeService!!.dataProcessing!!.buildTester()
            toggleTraining()

            if (!isStreaming){
                mStartButton!!.setText("Stop")
                isStreaming = true
            }else{
                mStartButton!!.setText("Start")
                isStreaming = false
            }
        }


        mStartButton!!.isEnabled = false

        simpleSeekBar = findViewById(R.id.seekbar)

        simpleSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                println(progress)
                mBluetoothLeService!!.dataProcessing.sensitivity = progress/100.00
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })



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
                actionBar!!.title = "Connecting"
                return true
            }
            R.id.menu_disconnect -> {
                mBluetoothLeService!!.disconnect()
                actionBar!!.title = "Disconnected"

                return true
            }
            R.id.help -> {
                val k = Intent(this, HelpActivity::class.java)
                startActivity(k)
            }
            R.id.trainOpen -> {
                mBluetoothLeService!!.isTraining = true
                mBluetoothLeService!!.eyesOpen = false
                toggleTraining()
                java.util.Timer().schedule(
                        object : java.util.TimerTask() {
                            override fun run() {
                                mBluetoothLeService!!.isTraining = false
                                mBluetoothLeService!!.eyesOpen = false
                                toggleTraining()
                                mBluetoothLeService!!.dataProcessing.buildTester()
                            }
                        }, 20000)
            }
            R.id.trainClose -> {
                mBluetoothLeService!!.isTraining = true
                mBluetoothLeService!!.eyesOpen = true
                toggleTraining()
                java.util.Timer().schedule(
                        object : java.util.TimerTask() {
                            override fun run() {
                                mBluetoothLeService!!.isTraining = false
                                mBluetoothLeService!!.eyesOpen = false
                                toggleTraining()
                                mBluetoothLeService!!.dataProcessing.buildTester()
                            }
                        }, 20000)
            }
            R.id.clearTraining -> {
                var file = File(applicationContext.filesDir.path.toString() + "braindata.csv")
                if (file.exists()) {
                    file.delete()
                }
                return true
            }
            R.id.settings -> {
                val k = Intent(this, SettingsActivity::class.java)
                startActivity(k)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                uuid = gattCharacteristic.uuid.toString()

                    charas.add(gattCharacteristic)
                    val currentCharaData = HashMap<String, String>()

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

            mStartButton!!.isEnabled = true
            actionBar!!.title = "Connected"
        }


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
