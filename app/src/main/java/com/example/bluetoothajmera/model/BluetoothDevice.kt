package com.example.bluetoothajmera.model

data class BltoothDevice(
    val devName: String,
    val address: String,
    val devClass: String,
    val signalStrength: Int,
    val pairedStatus: Boolean
)
