package com.zdravnica.bluetooth.data.models

sealed class BluetoothConnectionStatus {
    data object Connected : BluetoothConnectionStatus()
    data object Disconnected : BluetoothConnectionStatus()
    data class Error(val message: String) : BluetoothConnectionStatus()
}