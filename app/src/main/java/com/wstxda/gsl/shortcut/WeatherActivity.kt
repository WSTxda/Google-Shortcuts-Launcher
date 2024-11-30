package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.IntentHelper

class WeatherActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!launchGoogleWeather()) {
            launchWeatherInBrowser()
        }
        finish()
    }

    private fun launchGoogleWeather(): Boolean {
        val googleIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.weather.WeatherExportedActivity"
            )
        }

        return IntentHelper.tryStartActivity(this, googleIntent)
    }

    private fun launchWeatherInBrowser() {
        val weatherUrl = getString(R.string.weather_url)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(weatherUrl))

        if (!IntentHelper.tryStartActivity(this, browserIntent)) {
            IntentHelper.showToast(this, R.string.browser_not_found)
        }
    }
}