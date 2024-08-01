package com.zdravnica.bluetooth.data.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.zdravnica.bluetooth.models.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        macAddress = address
    )
}