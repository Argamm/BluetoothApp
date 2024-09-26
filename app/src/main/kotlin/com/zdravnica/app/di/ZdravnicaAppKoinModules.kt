package com.zdravnica.app.di

import com.zdravnica.bluetooth.di.bluetoothKoinModule
import org.koin.dsl.module


val appKoinModules = module {
    includes(
        bluetoothKoinModule,
        zdravnicaViewModelsKoinModules,
        dataStoreModule,
        calculateTemperatureModule,
        calculateCaloriesModule,
    )
}