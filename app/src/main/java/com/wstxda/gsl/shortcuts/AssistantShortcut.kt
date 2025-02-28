package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.ShortcutLauncher

class AssistantShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShortcuts(
            listOf(ShortcutLauncher.LaunchOption(createAssistantIntent())),
            R.string.google_not_found
        )
    }

    private fun createAssistantIntent(): Intent {
        return Intent(Intent.ACTION_VOICE_COMMAND)
    }
}