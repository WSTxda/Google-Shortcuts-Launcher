package com.wstxda.gsl.services

import android.annotation.SuppressLint
import android.content.Intent
import android.service.quicksettings.TileService
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.ActivityUtils

@Suppress("DEPRECATION")
class MusicSearchTileService : TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        val intent =
            Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (ActivityUtils.tryStartActivity(this, intent)) {
            startActivityAndCollapse(intent)
        } else {
            ActivityUtils.showToast(this, R.string.google_not_found)
        }
    }
}