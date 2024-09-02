package com.zdravnica.app.data

import android.content.SharedPreferences
import com.zdravnica.uikit.MIN_MINUTES
import com.zdravnica.uikit.MIN_TEMPERATURE

class SharedPreferencesDataStore(
    private val sharedPreferences: SharedPreferences
) : LocalDataStore {

    override fun saveTemperature(temperature: Int) {
        sharedPreferences.edit().putInt(KEY_TEMPERATURE, temperature).apply()
    }

    override fun getTemperature(): Int {
        return sharedPreferences.getInt(KEY_TEMPERATURE, MIN_TEMPERATURE)
    }

    override fun saveDuration(duration: Int) {
        sharedPreferences.edit().putInt(KEY_DURATION, duration).apply()
    }

    override fun getDuration(): Int {
        return sharedPreferences.getInt(KEY_DURATION, MIN_MINUTES)
    }

    companion object {
        private const val KEY_TEMPERATURE = "key_temperature"
        private const val KEY_DURATION = "key_duration"
    }
}
