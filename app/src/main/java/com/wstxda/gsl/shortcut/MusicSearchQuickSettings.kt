package com.wstxda.gsl.shortcut

import android.annotation.SuppressLint
import android.content.Intent
import android.service.quicksettings.TileService
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.IntentHelper

@Suppress("DEPRECATION")
class MusicSearchQuickSettings : TileService() {
    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()

        val musicSearchIntent =
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (!IntentHelper.tryStartActivity(this, musicSearchIntent)) {
            IntentHelper.showToast(this, R.string.google_not_found)
        } else {
            startActivityAndCollapse(musicSearchIntent)
        }
    }
}