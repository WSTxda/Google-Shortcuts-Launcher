package com.wstxda.gsl.shortcuts

import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.utils.IntentFactory

class SearchShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(IntentFactory.createSearchIntent()), R.string.google_not_found)
    }
}