package com.wstxda.gsl.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.showToast

abstract class ShortcutTileService : TileService() {

    @get:StringRes
    protected abstract val titleRes: Int

    @get:StringRes
    protected abstract val subtitleRes: Int

    @get:DrawableRes
    protected abstract val iconRes: Int

    protected abstract val targetActivity: Class<out ShortcutsActivity>
    protected abstract val tilePreferenceKey: String

    private val preferences by lazy { PreferenceHelper(applicationContext) }

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        super.onClick()

        if (!isTargetEnabled()) {
            showToast(R.string.shortcut_disabled)
            return
        }

        launchActivityAndCollapse(Intent(this, targetActivity))
    }

    override fun onTileAdded() {
        super.onTileAdded()
        preferences.setBoolean(tilePreferenceKey, true)
        updateTile()
    }

    override fun onTileRemoved() {
        preferences.setBoolean(tilePreferenceKey, false)
        super.onTileRemoved()
    }

    private fun updateTile() {
        val tile = qsTile ?: return
        val title = getText(titleRes)

        tile.state = Tile.STATE_INACTIVE
        tile.label = title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = getText(subtitleRes)
        }
        tile.icon = Icon.createWithResource(this, iconRes)
        tile.contentDescription = title
        tile.updateTile()
    }

    private fun isTargetEnabled(): Boolean {
        return when (packageManager.getComponentEnabledSetting(
            ComponentName(
                this, targetActivity
            )
        )) {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED -> false

            else -> true
        }
    }

    @SuppressLint("StartActivityAndCollapseDeprecated")
    private fun launchActivityAndCollapse(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("DEPRECATION") startActivityAndCollapse(intent)
        }
    }
}