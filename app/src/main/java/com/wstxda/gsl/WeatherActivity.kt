package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.widget.Toast

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
            showError(R.string.browser_not_found)
        }
    }

    private fun showError(messageResId: Int) {
        Toast.makeText(applicationContext, messageResId, Toast.LENGTH_SHORT).show()
    }
}