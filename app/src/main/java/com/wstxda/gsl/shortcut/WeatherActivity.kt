package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.wstxda.gsl.R

class WeatherActivity : Activity() {

    override fun onResume() {
        super.onResume()
        if (openWeatherInQuickSearch()) {
            return
        }
        openWeatherInBrowser()
    }

    private fun openWeatherInQuickSearch(): Boolean {
        val quickSearchIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.weather.WeatherExportedActivity"
            )
        }

        return try {
            startActivity(quickSearchIntent)
            finish()
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    private fun openWeatherInBrowser() {
        val weatherUrl = getString(R.string.weather_url)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(weatherUrl))

        try {
            startActivity(browserIntent)
            finish()
        } catch (e: ActivityNotFoundException) {
            showError(getString(R.string.browser_not_found))
        }
    }

    private fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}