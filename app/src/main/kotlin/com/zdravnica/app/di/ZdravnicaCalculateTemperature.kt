package com.zdravnica.app.di

import com.zdravnica.app.domain.CalculateTemperatureProgressUseCase
import org.koin.dsl.module

val calculateTemperatureModule = module {
    single { CalculateTemperatureProgressUseCase() }
}