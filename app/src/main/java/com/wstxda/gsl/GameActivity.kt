package com.wstxda.gsl

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.widget.Toast

class GameActivity : Activity() {
    override fun onResume() {
        super.onResume()

        // Try opening Game Turbo
        if (!tryOpenActivity(
                "com.miui.securitycenter", "com.miui.gamebooster.ui.GameBoosterMainActivity"
            )
        ) {
            // If unsuccessful, open Google Play Games
            openPlayGames()
        }

        finish()
    }

    private fun tryOpenActivity(packageName: String, className: String): Boolean {
        try {
            val intent = Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                component = ComponentName(packageName, className)
            }

            if (intent.component != null) {
                startActivity(intent)
                return true
            }
        } catch (e: Exception) {
            // Handle any exceptions
        }

        return false
    }

    private fun openPlayGames() {
        try {
            val playGamesPackageName = "com.google.android.play.games"
            val playGamesClassName =
                "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"

            val playGamesIntent = Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                component = ComponentName(playGamesPackageName, playGamesClassName)
            }

            if (playGamesIntent.component != null) {
                startActivity(playGamesIntent)
            } else {
                showPlayGamesNotFoundMessage()
            }
        } catch (e: Exception) {
            showPlayGamesNotFoundMessage()
        }
    }

    private fun showPlayGamesNotFoundMessage() {
        Toast.makeText(
            applicationContext, R.string.play_games_not_found, Toast.LENGTH_SHORT
        ).show()
    }
}

