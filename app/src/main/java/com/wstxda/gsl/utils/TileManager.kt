package com.wstxda.gsl.utils

import android.app.Activity
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.shortcut.MusicSearchQuickSettings
import com.wstxda.gsl.R
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.function.Consumer

class TileManager(private val context: Context) {

    fun requestAddTile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val tileServiceComponent = ComponentName(
                context, MusicSearchQuickSettings::class.java
            )

            val executor: Executor = Executors.newSingleThreadExecutor()

            val resultCallback = Consumer<Int> { result: Int ->
                val rootView = (context as? Activity)?.findViewById<View>(android.R.id.content)
                rootView?.let {
                    when (result) {
                        StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ADDED -> {
                            showSnackBar(it, context.getString(R.string.tile_added_success))
                        }

                        StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> {
                            showSnackBar(it, context.getString(R.string.tile_already_added))
                        }
                    }
                }
            }

            val statusBarManager = context.getSystemService(StatusBarManager::class.java)
            statusBarManager?.requestAddTileService(
                tileServiceComponent,
                context.getString(R.string.music_search_quick_settings),
                Icon.createWithResource(context, R.drawable.ic_music_search_tile),
                executor,
                resultCallback
            )
        }
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}