package com.zdravnica.app.di

import com.zdravnica.app.domain.CalculateCaloriesUseCase
import org.koin.dsl.module

val calculateCaloriesModule = module {
    single { CalculateCaloriesUseCase() }
}