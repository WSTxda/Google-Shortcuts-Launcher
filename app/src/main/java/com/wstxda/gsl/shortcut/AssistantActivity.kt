package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VOICE_COMMAND
import android.os.Bundle
import com.wstxda.gsl.utils.IntentHelper
import com.wstxda.gsl.R

class AssistantActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(ACTION_VOICE_COMMAND)
        if (!IntentHelper.tryStartActivity(this, intent)) {
            assistantNotFound()
        }
        finish()
    }

    private fun assistantNotFound() {
        IntentHelper.showToast(this, R.string.google_not_found)
    }
}