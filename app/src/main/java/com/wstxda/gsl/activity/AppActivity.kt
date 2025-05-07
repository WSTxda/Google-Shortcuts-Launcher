package com.wstxda.gsl.activity

import android.app.Application
import androidx.preference.PreferenceManager
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.ui.ThemeManager

class AppActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedTheme = prefs.getString(Constants.THEME_PREF_KEY, Constants.THEME_SYSTEM)
        selectedTheme?.let { ThemeManager.applyTheme(it) }
    }
}
