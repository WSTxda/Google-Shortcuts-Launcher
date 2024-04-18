package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log

class AssistantActivity : Activity() {
    companion object {
        private const val TAG = "AssistantActivity"
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        try {
            val intent = Intent(Intent.ACTION_VOICE_COMMAND)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            handleAssistantNotFound(e)
        }
        finish()
    }

    private fun handleAssistantNotFound(e: ActivityNotFoundException) {
        Log.e(TAG, "Unable to start assistant intent", e)
        finish()
    }
}