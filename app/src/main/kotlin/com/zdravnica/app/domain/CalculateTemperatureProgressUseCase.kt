package com.zdravnica.app.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CalculateTemperatureProgressUseCase {
    fun execute(sensorTemperature: Flow<Int>, targetTemperature: Int): Flow<Int> = flow {
        sensorTemperature.collect { currentTemperature ->
            if (currentTemperature <= targetTemperature) {
                val progress = calculateProgress(currentTemperature, targetTemperature)
                emit(progress)
            }else {
                emit(100)
            }
        }
    }

    private fun calculateProgress(currentTemperature: Int, targetTemperature: Int): Int {
        val progress = (currentTemperature * 100) / targetTemperature
        return progress.coerceIn(0, 100)
    }
}
