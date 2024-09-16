package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.wstxda.gsl.R

class MusicSearchActivity : Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        try {
            val intent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            handleMusicSearchNotFound()
        } finally {
            finish()
        }
    }

    private fun handleMusicSearchNotFound() {
        showToast(R.string.google_not_found)
    }

    private fun showToast(resId: Int) {
        Toast.makeText(applicationContext, resId, Toast.LENGTH_LONG).show()
    }
}