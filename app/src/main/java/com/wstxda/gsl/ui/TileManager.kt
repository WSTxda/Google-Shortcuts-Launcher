package com.wstxda.gsl.ui

import android.app.Activity
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.TileService
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.R

class TileManager(private val context: Context) {

    fun requestAddTile(
        serviceClass: Class<out TileService>,
        onResult: (Boolean) -> Unit,
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            onResult(false)
            return
        }

        val component = ComponentName(context, serviceClass)
        val serviceInfo = runCatching {
            @Suppress("DEPRECATION") context.packageManager.getServiceInfo(component, 0)
        }.getOrElse {
            onResult(false)
            return
        }
        val statusBarManager = context.getSystemService(StatusBarManager::class.java)
        if (statusBarManager == null) {
            onResult(false)
            return
        }

        val iconRes = serviceInfo.icon.takeIf { it != 0 } ?: context.applicationInfo.icon
        runCatching {
            statusBarManager.requestAddTileService(
                component,
                serviceInfo.loadLabel(context.packageManager),
                Icon.createWithResource(context, iconRes),
                context.mainExecutor,
            ) { result ->
                val added = when (result) {
                    StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ADDED -> {
                        showSnackBar(R.string.tile_added_success)
                        true
                    }

                    StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> {
                        showSnackBar(R.string.tile_already_added)
                        true
                    }

                    else -> false
                }
                onResult(added)
            }
        }.onFailure {
            onResult(false)
        }
    }

    private fun showSnackBar(messageRes: Int) {
        (context as? Activity)?.findViewById<View>(android.R.id.content)?.let { rootView ->
            Snackbar.make(rootView, messageRes, Snackbar.LENGTH_SHORT).show()
        }
    }
}