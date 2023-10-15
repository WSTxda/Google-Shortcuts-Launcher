package com.wstxda.gsl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.widget.Toast

@SuppressLint("Registered")
class WeatherActivity : Activity() {
    // android.app.Activity
    public override fun onResume() {
        super.onResume()
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.addCategory("android.intent.category.LAUNCHER")
            intent.component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.weather.WeatherExportedActivity"
            )
            startActivity(intent)
            finish()
        } catch (unused: ActivityNotFoundException) {
            openWeatherInBrowser()
        }
    }

    private fun openWeatherInBrowser() {
        val weatherUrl = "https://www.google.com/search?q=weather"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(weatherUrl))
        try {
            startActivity(browserIntent)
            finish()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, R.string.browser_not_found, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
