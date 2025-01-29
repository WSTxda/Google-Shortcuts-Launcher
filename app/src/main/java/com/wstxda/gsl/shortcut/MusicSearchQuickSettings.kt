package com.wstxda.gsl.shortcut

import android.content.Intent
import android.service.quicksettings.TileService
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.IntentHelper

class MusicSearchQuickSettings : TileService() {
    override fun onClick() {
        super.onClick()

        val musicSearchIntent =
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

        if (!IntentHelper.tryStartActivity(this, musicSearchIntent)) {
            IntentHelper.showToast(this, R.string.google_not_found)
        } else {
            startActivity(musicSearchIntent)
            collapse()
        }
    }
}