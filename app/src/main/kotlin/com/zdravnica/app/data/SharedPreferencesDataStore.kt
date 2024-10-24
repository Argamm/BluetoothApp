package com.zdravnica.app.data

import android.content.SharedPreferences
import android.util.Log
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
    override fun getBalmCount(balmName: String): Float {
        Log.i("adasdsa", "balmCount:  = ${sharedPreferences.getFloat(balmName, MAX_BALM_ML)},")
        return sharedPreferences.getFloat(balmName, MAX_BALM_ML)
    }

    override fun consumeBalm(balmName: String, consumption: Double) {
        val currentBalmCount = getBalmCount(balmName)
        val newBalmCount = (currentBalmCount - consumption).coerceAtLeast(0.0)
        Log.i("adasdsa", "consumeBalm: balmName = ${balmName}, newBalm = ${newBalmCount}")
        sharedPreferences.edit().putFloat(balmName, newBalmCount.toFloat()).apply()
    }

    override fun resetBalmCount(balmName: String) {
        sharedPreferences.edit().putFloat(balmName, MAX_BALM_ML).apply()
    }

    override fun resetBalmByName(balmName: String) {
        resetBalmCount(balmName)
    }

    // Command state methods
    override fun saveCommandState(command: String, isOn: Boolean) {
        sharedPreferences.edit().putBoolean(command, isOn).apply()
    }

    override fun getCommandState(command: String): Boolean {
        val state = sharedPreferences.getBoolean(command, false)
        return state
    }

    override fun saveFailSendingCommand(command: String, failed: Boolean) {
        sharedPreferences.edit().putBoolean(command, failed).apply()
    }

    override fun getIsFailedSendingCommand(command: String): Boolean {
        return sharedPreferences.getBoolean(command, false)
    }

    override fun saveAllCommandsAreTurnedOff() {
        sharedPreferences.edit().putBoolean(KEY_TURNED_OFF_COMMANDS, true).apply()
    }

    override fun getAllCommandsAreTurnedOff(): Boolean {
        return sharedPreferences.getBoolean(KEY_TURNED_OFF_COMMANDS, false)
    }

    companion object {
        private const val KEY_TEMPERATURE = "key_temperature"
        private const val KEY_DURATION = "key_duration"
        private const val KEY_TURNED_OFF_COMMANDS = "key_turned_off_commands"
        private const val MAX_BALM_ML = 6f
    }
}
