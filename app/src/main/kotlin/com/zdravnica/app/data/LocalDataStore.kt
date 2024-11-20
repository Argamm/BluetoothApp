package com.zdravnica.app.data

interface LocalDataStore {
    fun saveTemperature(temperature: Int)
    fun getTemperature(): Int
    fun saveDuration(duration: Int)
    fun getDuration(): Int

    fun getBalmCount(balmName: String): Float
    fun consumeBalm(balmName: String, consumption: Double)
    fun resetBalmCount(balmName: String)
    fun resetBalmByName(balmName: String)

    fun saveCommandState(command: String, isOn: Boolean)
    fun getCommandState(command: String): Boolean
    fun saveFailSendingCommand(command: String, failed: Boolean)
    fun getIsFailedSendingCommand(command: String): Boolean
    fun saveAllCommandsAreTurnedOff()
    fun getAllCommandsAreTurnedOff(): Boolean
    fun setIREMActive(isActive: Boolean)
    fun getIREMActive(): Boolean
}