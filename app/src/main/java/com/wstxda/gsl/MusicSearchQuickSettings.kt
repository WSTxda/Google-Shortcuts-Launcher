package com.wstxda.gsl

import android.content.Intent
import android.service.quicksettings.TileService

class MusicSearchQuickSettings : TileService() {
    override fun onClick() {
        super.onClick()

        val musicSearchIntent =
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

        try {
            startActivityAndCollapse(musicSearchIntent)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun handleError(e: Exception) {
    }
}
