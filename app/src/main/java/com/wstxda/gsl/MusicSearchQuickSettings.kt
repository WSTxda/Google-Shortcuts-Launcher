package com.wstxda.gsl

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService

@Suppress("DEPRECATION")
class MusicSearchQuickSettings : TileService() {
    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            /* use PendingIntent for the SDK and above */
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            pendingIntent.send()
        } else {
            /* use normal Intent for the SDK 33 and lower */
            startActivityAndCollapse(
                Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
        }
    }
}
