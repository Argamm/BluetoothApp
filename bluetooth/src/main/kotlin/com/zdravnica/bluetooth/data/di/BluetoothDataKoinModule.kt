package com.zdravnica.bluetooth.data.di

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import com.zdravnica.bluetooth.data.AndroidBluetoothController
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val bluetoothDataKoinModule = module {
    singleOf(::provideBluetoothManager)
    singleOf(::provideBluetoothAdapter)
    singleOf(::AndroidBluetoothController) { bind<BluetoothController>() }
}


private fun provideBluetoothAdapter(
    bluetoothManager: BluetoothManager
): BluetoothAdapter? = bluetoothManager.adapter

private fun provideBluetoothManager(
    application: Application
): BluetoothManager = application.getSystemService(BluetoothManager::class.java)



