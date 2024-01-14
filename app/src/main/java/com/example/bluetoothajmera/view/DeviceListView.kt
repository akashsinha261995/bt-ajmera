package com.example.bluetoothajmera.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothajmera.R
import com.example.bluetoothajmera.model.BtDevice
import com.example.bluetoothajmera.viewmodel.BtUIState

@Composable
fun DeviceListView(
    state: BtUIState,
    onStartSearch: () -> Unit,
    onStopScan: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DeviceList(pairedDevice = state.pairedDevice,
            scannedDevices = state.scannedDevice,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
    )   {
            Button(onClick = onStartSearch) {
                Text(text = stringResource(R.string.start_scan))
            }

            Button(onClick = onStopScan) {
                Text(text = stringResource(R.string.stop_scan))
            }

        }

    }

}

@Composable
fun DeviceList(
    pairedDevice: List<BtDevice>,
    scannedDevices: List<BtDevice>,
    onClick: (BtDevice) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ){
        item{
            Text(
                text = stringResource(R.string.paired_devices),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevice) {device ->
            Text(
                text = device.devName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            )
        }

        item{
            Text(
                text = stringResource(R.string.scanned_devices),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) {device ->
            Text(
                text = device.devName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            )
        }
    }
}