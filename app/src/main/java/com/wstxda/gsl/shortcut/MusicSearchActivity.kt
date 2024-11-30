package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.wstxda.gsl.utils.IntentHelper
import com.wstxda.gsl.R

class MusicSearchActivity : Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val musicSearchIntent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
        if (!IntentHelper.tryStartActivity(this, musicSearchIntent)) {
            IntentHelper.showToast(this, R.string.google_not_found)
        }
        finish()
    }
}