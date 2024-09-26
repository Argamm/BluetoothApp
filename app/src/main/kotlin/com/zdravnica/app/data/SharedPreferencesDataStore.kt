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

    // Balm related methods
    override fun getBalmCount(balmName: String): Int {
        return sharedPreferences.getInt(balmName, MAX_BALM_ML)
    }

    override fun consumeBalm(balmName: String, consumption: Int) {
        val currentBalmCount = getBalmCount(balmName)
        val newBalmCount = (currentBalmCount - consumption).coerceAtLeast(0)
        sharedPreferences.edit().putInt(balmName, newBalmCount).apply()
    }

    override fun resetBalmCount(balmName: String) {
        sharedPreferences.edit().putInt(balmName, MAX_BALM_ML).apply()
    }

    override fun resetBalmByName(balmName: String) {
        resetBalmCount(balmName)
    }

    override fun saveCommandState(command: String, isOn: Boolean) {
        sharedPreferences.edit().putBoolean(command, isOn).apply()
    }

    override fun getCommandState(command: String): Boolean {
        return sharedPreferences.getBoolean(command, false)
    }

    companion object {
        private const val KEY_TEMPERATURE = "key_temperature"
        private const val KEY_DURATION = "key_duration"
        private const val MAX_BALM_ML = 100
    }
}
