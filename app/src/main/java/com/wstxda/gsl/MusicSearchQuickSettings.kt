package com.wstxda.gsl

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class MusicSearchQuickSettings : TileService() {

    override fun onClick() {
        super.onClick()

        if (qsTile.state == Tile.STATE_ACTIVE) {
            val intent = Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            try {
                startActivityAndCollapse(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
