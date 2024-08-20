package com.zdravnica.app

import android.content.Context
import android.content.SharedPreferences
import com.zdravnica.uikit.extensions.compose.MIN_MINUTES
import com.zdravnica.uikit.extensions.compose.MIN_TEMPERATURE

object PreferencesHelper {
    private const val PREF_NAME = "SelectProcedurePreferences"
    private const val KEY_TEMPERATURE = "key_temperature"
    private const val KEY_DURATION = "key_duration"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveTemperature(context: Context, temperature: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(KEY_TEMPERATURE, temperature)
        editor.apply()
    }

    fun getTemperature(context: Context): Int {
        return getPreferences(context).getInt(KEY_TEMPERATURE, MIN_TEMPERATURE)
    }

    fun saveDuration(context: Context, duration: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(KEY_DURATION, duration)
        editor.apply()
    }

    fun getDuration(context: Context): Int {
        return getPreferences(context).getInt(KEY_DURATION, MIN_MINUTES)
    }
}