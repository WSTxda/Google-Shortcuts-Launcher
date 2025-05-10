package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class IncognitoShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(createIncognitoIntent()), R.string.chrome_not_found)
    }

    private fun createIncognitoIntent() = Intent().apply {
        component = ComponentName(
            "com.android.chrome", "org.chromium.chrome.browser.incognito.IncognitoTabLauncher"
        )
    }
}