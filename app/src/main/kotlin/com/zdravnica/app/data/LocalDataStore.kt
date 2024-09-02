package com.zdravnica.app.data

interface LocalDataStore {
    fun saveTemperature(temperature: Int)
    fun getTemperature(): Int
    fun saveDuration(duration: Int)
    fun getDuration(): Int
}