package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class SearchShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(createSearchIntent()), R.string.google_not_found)
    }

    private fun createSearchIntent() = Intent().apply {
        component = ComponentName(
            "com.google.android.googlequicksearchbox",
            "com.google.android.search.core.google.GoogleSearch"
        )
    }
}