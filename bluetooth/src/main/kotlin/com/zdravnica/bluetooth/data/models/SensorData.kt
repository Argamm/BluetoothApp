package com.zdravnica.bluetooth.data.models

data class SensorData(
    val temrTmpr1: Int,
    val temrIR1: Double,
    val temrIR2: Double,
    val skinTemperature: Double,
    val snsrHC: Int,
    val thermostat: Boolean,
    val stateDevice: Int
)
