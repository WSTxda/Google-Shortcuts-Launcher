package com.wstxda.gsl.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.os.Build
import android.service.quicksettings.TileService
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.IntentFactory

class MusicSearchTileService : TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        val intent = IntentFactory.createMusicSearchIntent()
        if (!launchShortcuts(intent)) {
            showToast(R.string.google_not_found)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                val pendingIntent = PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_IMMUTABLE
                )
                startActivityAndCollapse(pendingIntent)
            } else {
                @Suppress("DEPRECATION") startActivityAndCollapse(intent)
            }
        }
    }
}