package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class AssistantShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(createAssistantIntent()), R.string.google_not_found)
    }

    private fun createAssistantIntent() = Intent(Intent.ACTION_VOICE_COMMAND)
}