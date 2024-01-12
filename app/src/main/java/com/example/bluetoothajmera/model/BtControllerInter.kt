package com.example.bluetoothajmera.model

import kotlinx.coroutines.flow.StateFlow

interface BtControllerInter {

    // The UI collects from this StateFlow and list and update all the devices in the range
    val scannedDevices: StateFlow<List<BtDevice>>

    // The UI collects from this StateFlow and list all the devices that are paired
    val pairedDevice: StateFlow<List<BtDevice>>

    fun searchDevice()
    fun stopSearch()
    fun release()
}