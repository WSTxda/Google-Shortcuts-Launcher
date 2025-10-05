package com.wstxda.gsl.shortcuts

import android.content.Intent
import androidx.core.net.toUri
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class WeatherShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(
            listOf(
                createWeatherIntent(),
                Intent(Intent.ACTION_VIEW, "https://www.google.com/search?q=weather".toUri())
            ), R.string.browser_not_found
        )
    }

    private fun createWeatherIntent() = Intent().apply {
        setClassName(
            "com.google.android.googlequicksearchbox",
            "com.google.android.apps.search.weather.WeatherExportedActivity"
        )
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
}