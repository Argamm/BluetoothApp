package com.zdravnica.bluetooth.di

import com.zdravnica.bluetooth.data.di.bluetoothDataKoinModule
import org.koin.dsl.module

val bluetoothKoinModule = module {
    includes(bluetoothDataKoinModule)
}