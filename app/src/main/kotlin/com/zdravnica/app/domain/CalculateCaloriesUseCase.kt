package com.zdravnica.app.domain

import kotlin.math.roundToInt

class CalculateCaloriesUseCase {
    private var previousPulse = 0
    private var totalCalories = 0.0

    fun calculateCalories(pulse: Int, isTimerFinished: Boolean): Int {
        if (!isTimerFinished && pulse in 57..209) {
            previousPulse = pulse
            val calorieValue = ((pulse * 2) - 113) / 200.0
            totalCalories += calorieValue
        } else {
            previousPulse
        }

        return totalCalories.roundToInt()
    }

    fun resetCalories() {
        totalCalories = 0.0
        previousPulse = 0
    }
}