package com.example.bluetoothajmera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothajmera.control.BtController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class BtViewModel @Inject constructor(
    private val btController: BtController
): ViewModel(){

    private val _state = MutableStateFlow(BtUIState())
    val state = combine(
        btController.scannedDevices,
        btController.pairedDevice,
        _state
    ){
        scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevice = scannedDevices,
            pairedDevice = pairedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun deviceScan() {
        btController.searchDevice()
    }

    fun stopScan() {
        btController.stopSearch()
    }

}