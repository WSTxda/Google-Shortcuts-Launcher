package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent

class LensActivity : Activity() {

    override fun onResume() {
        super.onResume()
        if (launchGoogleLens()) {
            return
        }
    }

    private fun launchGoogleLens(): Boolean {
        val lensIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
            component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.lens.LensExportedActivity"
            )
        }

        return try {
            startActivity(lensIntent)
            finish()
            true
        } catch (_: ActivityNotFoundException) {
            false
        }
    }
}