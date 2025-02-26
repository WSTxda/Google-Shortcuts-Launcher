package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class AssistantShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        val intent = Intent(Intent.ACTION_VOICE_COMMAND)
        if (!tryStartActivity(intent)) {
            showToast(R.string.google_not_found)
        }
    }
}