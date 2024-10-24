package com.zdravnica.bluetooth.domain.controller

import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.data.models.SensorData
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannedDevices: StateFlow<List<BluetoothDeviceDomainModel>>
    val pairedDevices: StateFlow<List<BluetoothDeviceDomainModel>>
    val connectionResultFlow: SharedFlow<ConnectionResult>
    val showLoading: SharedFlow<Boolean>
    val sensorDataFlow: StateFlow<SensorData?>
    val bluetoothConnectionStatus: StateFlow<BluetoothConnectionStatus>
//    val getCommandsState: SharedFlow<String>

    fun bluetoothIsEnabled(): Boolean
    fun refreshPairedDevices()
    fun connect(device: BluetoothDeviceDomainModel): Flow<ConnectionResult>
    fun close()
    fun startScanning()
    fun stopScanning()
    suspend fun sendCommand(
        cmd: String,
        onSuccess: (() -> Unit)? = null,
        onFailed: (() -> Unit)? = null
    )
}