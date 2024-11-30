package com.wstxda.gsl.shortcut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.GamesBrandsPackages
import com.wstxda.gsl.utils.IntentHelper

class GamesActivity : AppCompatActivity() {

    companion object {
        private const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchGames()
    }

    private fun launchGames() {
        val useGameManager = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(GAME_MANAGER_PREF_KEY, false)

        if (useGameManager) {
            if (!launchBrandGamesManager()) {
                IntentHelper.showToast(this, R.string.game_manager_not_found)
            }
        } else {
            if (!launchGooglePlayGamesFolder()) {
                IntentHelper.showToast(this, R.string.play_games_not_found)
            }
        }
        finish()
    }

    private fun launchBrandGamesManager(): Boolean {
        val context = this
        return GamesBrandsPackages.gamesLaunchersIntents.any { (packageName, className) ->
            IntentHelper.tryStartActivity(context, packageName, className)
        }
    }

    private fun launchGooglePlayGamesFolder(): Boolean {
        return IntentHelper.tryStartActivity(
            this, "com.google.android.play.games", "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
        )
    }
}