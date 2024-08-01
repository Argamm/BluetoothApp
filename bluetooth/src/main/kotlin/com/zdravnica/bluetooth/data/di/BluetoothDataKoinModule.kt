package com.zdravnica.bluetooth.data.di

import com.zdravnica.bluetooth.data.AndroidBluetoothController
import com.zdravnica.bluetooth.data.BluetoothController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val bluetoothDataKoinModule = module {
    singleOf(::AndroidBluetoothController) { bind<BluetoothController>() }
}

