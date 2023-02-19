package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MusicSearchActivity : Activity() {
    // android.app.Activity
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val intent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, getString(R.string.unable_start) + intent, e)
            Toast.makeText(applicationContext, R.string.activity_not_found, Toast.LENGTH_LONG)
                .show()
        }
        finish()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}