package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.ShortcutLauncher

class LensShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShortcuts(
            listOf(ShortcutLauncher.LaunchOption(createLensIntent())), R.string.google_not_found
        )
    }

    private fun createLensIntent(): Intent {
        return Intent().apply {
            component = ComponentName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.search.lens.LensExportedActivity"
            )
            flags = Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
        }
    }
}