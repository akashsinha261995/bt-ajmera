package com.example.bluetoothajmera.viewmodel

import com.example.bluetoothajmera.model.BtDevice

data class BtUIState(
    val scannedDevice: List<BtDevice> = emptyList(),
    val pairedDevice: List<BtDevice> = emptyList()
)
