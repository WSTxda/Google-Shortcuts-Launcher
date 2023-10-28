package com.wstxda.gsl

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.widget.Toast

class GameActivity : Activity() {
    override fun onResume() {
        super.onResume()
        try {
            val packageName = "com.google.android.play.games"
            val className =
                "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"

            val intent = Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                component = ComponentName(packageName, className)
            }

            if (intent.component != null) {
                startActivity(intent)
            } else {
                showPlayGamesNotFoundMessage()
            }
        } catch (e: Exception) {
            showPlayGamesNotFoundMessage()
        }

        finish()
    }

    private fun showPlayGamesNotFoundMessage() {
        Toast.makeText(
            applicationContext, R.string.play_games_not_found, Toast.LENGTH_SHORT
        ).show()
    }
}


