package com.zdravnica.app.data

interface LocalDataStore {
    fun saveTemperature(temperature: Int)
    fun getTemperature(): Int
    fun saveDuration(duration: Int)
    fun getDuration(): Int

    // Balm related methods
    fun getBalmCount(balmName: String): Int
    fun consumeBalm(balmName: String, consumption: Int)
    fun resetBalmCount(balmName: String)
    fun resetBalmByName(balmName: String)

    // Command state methods
    fun saveCommandState(command: String, isOn: Boolean)
    fun getCommandState(command: String): Boolean
}