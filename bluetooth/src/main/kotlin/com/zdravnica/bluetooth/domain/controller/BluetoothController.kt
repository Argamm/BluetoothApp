package com.zdravnica.bluetooth.domain.controller

import com.zdravnica.bluetooth.data.models.BluetoothDeviceDataModel
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.data.models.InfoDataModel
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDeviceDomainModel>>
    val pairedDevices: StateFlow<List<BluetoothDeviceDomainModel>>

    fun bluetoothIsEnabled(): Boolean
    fun refreshPairedDevices()
    fun connect(device: BluetoothDeviceDataModel): Flow<ConnectionResult>
    fun close()

    suspend fun sendData(sendData: InfoDataModel): InfoDataModel?

}