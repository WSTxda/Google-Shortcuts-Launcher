package com.wstxda.gsl.logic

import android.content.Context
import androidx.preference.PreferenceManager

class PreferenceHelper(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        preferences.getBoolean(key, defaultValue)

    fun getString(key: String, defaultValue: String? = null): String? =
        preferences.getString(key, defaultValue)
}