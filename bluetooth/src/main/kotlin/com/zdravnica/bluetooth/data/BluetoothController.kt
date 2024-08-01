package com.zdravnica.bluetooth.data

import com.zdravnica.bluetooth.models.BluetoothDevice
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()

    fun isEnabledBluetooth(): Boolean
}