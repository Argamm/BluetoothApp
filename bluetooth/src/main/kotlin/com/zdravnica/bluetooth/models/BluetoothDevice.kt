package com.zdravnica.bluetooth.models

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val macAddress: String
)
