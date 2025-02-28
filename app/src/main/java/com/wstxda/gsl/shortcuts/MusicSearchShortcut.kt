package com.wstxda.gsl.shortcuts

import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.IntentFactory
import com.wstxda.gsl.utils.ShortcutLauncher

class MusicSearchShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShortcuts(
            listOf(ShortcutLauncher.LaunchOption(IntentFactory.createMusicSearchIntent())),
            R.string.google_not_found
        )
    }
}