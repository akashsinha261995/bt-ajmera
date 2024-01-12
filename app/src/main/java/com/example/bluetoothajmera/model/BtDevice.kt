package com.example.bluetoothajmera.model

data class BtDevice(
    val devName: String,
    val address: String,
    val devClass: Int,
    val signalStrength: Short
)
