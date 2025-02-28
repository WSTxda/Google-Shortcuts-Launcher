package com.wstxda.gsl.logic

import android.content.Context
import androidx.preference.PreferenceManager

class PreferenceHelper(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }
}