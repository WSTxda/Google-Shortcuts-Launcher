package com.wstxda.gsl

import android.content.Intent
import android.service.quicksettings.TileService

class MusicSearchQuickSettings : TileService() {
    // android.service.quicksettings.TileService
    override fun onClick() {
        super.onClick()
        startActivityAndCollapse(
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }
}