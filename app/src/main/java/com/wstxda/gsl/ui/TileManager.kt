package com.wstxda.gsl.ui

import android.app.Activity
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.R
import com.wstxda.gsl.services.MusicSearchTileService
import java.util.concurrent.Executors
import java.util.function.Consumer

class TileManager(private val context: Context) {
    fun requestAddTile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val tileServiceComponent = ComponentName(context, MusicSearchTileService::class.java)
            val executor = Executors.newSingleThreadExecutor()
            val resultCallback = Consumer<Int> { result ->
                (context as? Activity)?.findViewById<View>(android.R.id.content)?.let { rootView ->
                    val message = when (result) {
                        StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ADDED -> context.getString(R.string.tile_added_success)

                        StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> context.getString(
                            R.string.tile_already_added
                        )

                        else -> ""
                    }
                    if (message.isNotEmpty()) showSnackBar(rootView, message)
                }
            }
            context.getSystemService(StatusBarManager::class.java)?.requestAddTileService(
                tileServiceComponent,
                context.getString(R.string.music_search_label),
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