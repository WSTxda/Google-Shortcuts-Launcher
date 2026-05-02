package com.wstxda.gsl.service

import android.content.Intent
import android.service.quicksettings.Tile
import androidx.core.graphics.drawable.IconCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.ui.utils.ShortcutResourcesManager
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.ShortcutsMap.shortcuts

class ShortcutTileService : BaseTileService() {

    private val preferenceHelper by lazy { PreferenceHelper(applicationContext) }
    private val resourcesManager by lazy { ShortcutResourcesManager(applicationContext) }

    override fun onClick() {
        val key = preferenceHelper.getString(Constants.SHORTCUT_TILE_PREF_KEY, null) ?: return
        val activityClass = shortcuts[key]

        if (activityClass == null) {
            showToast(R.string.shortcut_invalid)
            return
        }

        val validationIntent = Intent(this, activityClass)
        if (!launchShortcuts(validationIntent)) {
            showToast(R.string.shortcut_disabled)
            return
        }

        startActivityAndCollapse(activityClass)
    }

    override fun updateTile() {
        val key = preferenceHelper.getString(Constants.SHORTCUT_TILE_PREF_KEY, null)
        val iconRes = resourcesManager.getShortcutIcon(key)
        val label = resourcesManager.getShortcutName(key)

        setTileState(
            state = Tile.STATE_INACTIVE,
            label = label,
            subtitle = getString(R.string.shortcut_open),
            icon = IconCompat.createWithResource(this, iconRes).toIcon(this)
        )
    }
}