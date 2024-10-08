package com.wstxda.gsl

import android.annotation.SuppressLint
import android.content.Intent
import android.service.quicksettings.TileService

@Suppress("DEPRECATION")
class MusicSearchQuickSettings : TileService() {
    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        startActivityAndCollapse(
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }
}