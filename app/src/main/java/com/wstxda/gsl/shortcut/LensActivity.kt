package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.wstxda.gsl.utils.IntentHelper
import com.wstxda.gsl.R

class LensActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!launchGoogleLens()) {
            IntentHelper.showToast(this, R.string.google_not_found)
        }
        finish()
    }

    private fun launchGoogleLens(): Boolean {
        val lensIntent = Intent().apply {
            component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.lens.LensExportedActivity"
            )
            flags = Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
        }

        return IntentHelper.tryStartActivity(this, lensIntent)
    }
}