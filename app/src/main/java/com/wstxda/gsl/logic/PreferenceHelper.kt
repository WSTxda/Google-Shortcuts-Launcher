package com.wstxda.gsl.logic

import android.content.Context
import androidx.preference.PreferenceManager
import com.wstxda.gsl.utils.Constants
import androidx.core.content.edit

class PreferenceHelper(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        preferences.getBoolean(key, defaultValue)

    fun getString(key: String, defaultValue: String? = null): String? =
        preferences.getString(key, defaultValue)

    fun setBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getShortcutLaunchDelayMillis(): Long =
        getString(Constants.SHORTCUT_LAUNCH_DELAY_PREF_KEY)?.toLongOrNull()
            ?.coerceIn(0L, Constants.SHORTCUT_LAUNCH_DELAY_MAX_MS.toLong()) ?: 0L
}