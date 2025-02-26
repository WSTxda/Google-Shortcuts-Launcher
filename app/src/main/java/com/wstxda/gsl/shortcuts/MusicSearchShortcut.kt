package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class MusicSearchShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        val intent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
        if (!tryStartActivity(intent)) {
            showToast(R.string.google_not_found)
        }
    }
}