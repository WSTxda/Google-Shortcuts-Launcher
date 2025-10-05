package com.wstxda.gsl.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.core.graphics.drawable.IconCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.ShortcutsMap.shortcuts
import com.wstxda.gsl.ui.utils.ShortcutResourcesManager

class ShortcutTileService : TileService() {

    private val preferenceHelper by lazy { PreferenceHelper(this) }
    private val resourcesManager by lazy { ShortcutResourcesManager(this) }

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onTileAdded() {
        super.onTileAdded()
        updateTile()
    }

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        val key = preferenceHelper.getString(Constants.TILE_SHORTCUT_PREF_KEY, null) ?: return
        val activityClass = shortcuts[key]

        if (activityClass == null) {
            showToast(R.string.shortcut_invalid)
            return
        }

        val intent = Intent(this, activityClass).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        if (!launchShortcuts(intent)) {
            showToast(R.string.shortcut_disabled)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("DEPRECATION") startActivityAndCollapse(intent)
        }
    }

    private fun updateTile() {
        val tile = qsTile ?: return
        val key = preferenceHelper.getString(Constants.TILE_SHORTCUT_PREF_KEY, null)
        val iconRes = resourcesManager.getShortcutIcon(key)
        val label = resourcesManager.getShortcutName(key)

        tile.apply {
            icon = IconCompat.createWithResource(this@ShortcutTileService, iconRes)
                .toIcon(this@ShortcutTileService)
            this.label = label
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.shortcut_open)
            }
            state = Tile.STATE_INACTIVE
            updateTile()
        }
    }
}