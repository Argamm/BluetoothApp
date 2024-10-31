package com.zdravnica.uikit.components.clock

import android.os.CountDownTimer
import com.zdravnica.uikit.DELAY_1000_ML

class Clock(private val duration: Long, private val tickInterval: Long = DELAY_1000_ML) {
    private var countDownTimer: CountDownTimer? = null

    fun start(onFinish: () -> Unit, onTick: (remainingTime: Long) -> Unit) {
        countDownTimer = object : CountDownTimer(duration, tickInterval) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }

    fun cancel() {
        countDownTimer?.cancel()
    }
}