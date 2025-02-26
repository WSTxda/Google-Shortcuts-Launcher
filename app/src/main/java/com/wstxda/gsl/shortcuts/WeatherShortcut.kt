package com.wstxda.gsl.shortcuts

import android.content.Intent
import android.net.Uri
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class WeatherShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        val packageName = "com.google.android.googlequicksearchbox"
        val className = "com.google.android.apps.search.weather.WeatherExportedActivity"
        if (!tryStartActivity(packageName, className)) {
            launchWeatherInBrowser()
        }
    }

    private fun launchWeatherInBrowser() {
        val weatherUrl = getString(R.string.weather_url)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(weatherUrl))
        if (!tryStartActivity(browserIntent)) {
            showToast(R.string.browser_not_found)
        }
    }
}