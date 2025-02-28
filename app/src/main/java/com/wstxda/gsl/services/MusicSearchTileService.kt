package com.wstxda.gsl.services

import android.annotation.SuppressLint
import android.service.quicksettings.TileService
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.ActivityUtils
import com.wstxda.gsl.utils.IntentFactory
import com.wstxda.gsl.utils.ShortcutLauncher

class MusicSearchTileService : TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        val intent = IntentFactory.createMusicSearchIntent()
        val launcher = ShortcutLauncher(this)
        if (launcher.launch(ShortcutLauncher.LaunchOption(intent))) {
            @Suppress("DEPRECATION") startActivityAndCollapse(intent)
        } else {
            ActivityUtils.showToast(this, R.string.google_not_found)
        }
    }
}