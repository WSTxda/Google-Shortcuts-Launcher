package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log

class AssistantActivity : Activity() {

    companion object {
        private const val TAG = "AssistantActivity"
        private const val VOICE_COMMAND_ACTION = Intent.ACTION_VOICE_COMMAND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val intent = Intent(VOICE_COMMAND_ACTION)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            handleAssistantNotFound(e)
        } finally {
            finish()
        }
    }

    private fun handleAssistantNotFound(e: ActivityNotFoundException) {
        Log.e(TAG, "Unable to start assistant intent", e)
    }
}