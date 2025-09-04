package com.wstxda.gsl

import android.app.Application
import androidx.preference.PreferenceManager
import com.wstxda.gsl.ui.ThemeManager
import com.wstxda.gsl.utils.Constants

class GSL : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedTheme = prefs.getString(Constants.THEME_PREF_KEY, Constants.THEME_SYSTEM)
        selectedTheme?.let { ThemeManager.applyTheme(it) }
    }
}