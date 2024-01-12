package com.example.bluetoothajmera.control

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.bluetoothajmera.model.BtControllerInter
import com.example.bluetoothajmera.model.BtDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
class BtController(
    private val context: Context, private val intent: Intent
): BtControllerInter {

    private val btManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        btManager?.adapter
    }

    private val _scannedDevices = MutableStateFlow<List<BtDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BtDevice>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevice = MutableStateFlow<List<BtDevice>>(emptyList())
    override val pairedDevice: StateFlow<List<BtDevice>>
        get() = _pairedDevice.asStateFlow()

    init {
        updatePairedDevice()
    }
    override fun searchDevice() {
        if (!hasPermissions(Manifest.permission.BLUETOOTH_SCAN))
            return
        updatePairedDevice()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopSearch() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    private fun hasPermissions(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun updatePairedDevice(){
        if(!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)){
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.mapBtDevice(intent) }
            ?.also { devices -> _pairedDevice.update { devices } }
    }

}