package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.ShortcutLauncher
import androidx.core.net.toUri

class WeatherShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShortcuts(
            listOf(
                ShortcutLauncher.LaunchOption(createWeatherIntent()),
                ShortcutLauncher.LaunchOption(createBrowserIntent())
            ), R.string.browser_not_found
        )
    }

    private fun createWeatherIntent(): Intent {
        return Intent().apply {
            setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.weather.WeatherExportedActivity"
            )
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
    }

    private fun createBrowserIntent(): Intent {
        return Intent(Intent.ACTION_VIEW, getString(R.string.weather_url).toUri())
    }
}