package com.zdravnica.bluetooth.data.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.zdravnica.bluetooth.data.models.BluetoothDeviceDataModel
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDevice(): BluetoothDeviceDataModel {
    return BluetoothDeviceDataModel(
        name = name,
        macAddress = address
    )
}

fun BluetoothDeviceDataModel.toBluetoothDeviceDomain() = BluetoothDeviceDomainModel(
    name = name,
    macAddress =  macAddress
)