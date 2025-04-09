package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcutsHistory
import com.wstxda.gsl.logic.showToast

class LensShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        if (!launchShortcutsHistory(createLensIntent())) {
            showToast(R.string.google_not_found)
        }
    }

    private fun createLensIntent() = Intent().apply {
        component = ComponentName(
            "com.google.android.googlequicksearchbox",
            "com.google.android.apps.search.lens.LensExportedActivity"
        )
    }
}