package com.example.bluetoothajmera.control

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Intent
import com.example.bluetoothajmera.model.BtDevice

//extension function to map device details


@SuppressLint("MissingPermission")
fun BluetoothDevice.mapBtDevice( intent: Intent): BtDevice {
    return BtDevice(
        devName = name,
        address = address,
        devClass = bluetoothClass.deviceClass,
        signalStrength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE),
    )
}