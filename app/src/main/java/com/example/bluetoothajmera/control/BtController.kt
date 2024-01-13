package com.example.bluetoothajmera.control

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.example.bluetoothajmera.broadcastReceiver.FoundDeviceReceiver
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

    private val foundDeviceReceiver = FoundDeviceReceiver {device ->
        _scannedDevices.update { devices ->
            val newDevice = device.mapBtDevice(intent)
            if(newDevice in devices) devices else devices + newDevice
        }

    }

    init {
        updatePairedDevice()
    }
    override fun searchDevice() {
        if (!hasPermissions(Manifest.permission.BLUETOOTH_SCAN))
            return

        context.registerReceiver(foundDeviceReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        updatePairedDevice()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopSearch() {
        if (!hasPermissions(Manifest.permission.BLUETOOTH_SCAN))
            return
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
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