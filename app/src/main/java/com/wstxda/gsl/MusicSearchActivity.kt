package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MusicSearchActivity : Activity() {
    companion object {
        private const val TAG = "MusicSearchActivity"
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val intent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            handleMusicSearchNotFound(e)
        }
        finish()
    }

    private fun handleMusicSearchNotFound(e: ActivityNotFoundException) {
        Log.e(TAG, "Unable to start music search intent", e)
        showToast(R.string.google_not_found, Toast.LENGTH_LONG)
        finish()
    }

    private fun showToast(resId: Int, duration: Int) {
        Toast.makeText(applicationContext, resId, duration).show()
    }
}
