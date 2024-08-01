package com.zdravnica.bluetooth.data

import android.bluetooth.BluetoothDevice

class FoundDeviceReceiver (
    private val onDeviceFound: (BluetoothDevice) -> Unit
){

}