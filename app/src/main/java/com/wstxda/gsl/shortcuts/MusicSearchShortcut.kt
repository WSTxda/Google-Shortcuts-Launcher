package com.wstxda.gsl.shortcuts

import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.utils.IntentFactory

class MusicSearchShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(IntentFactory.createMusicSearchIntent()), R.string.google_not_found)
    }
}