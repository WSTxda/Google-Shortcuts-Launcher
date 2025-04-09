package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.GamesBrandsPackages

class GamesShortcut : ShortcutsActivity() {
    private val preferences by lazy { PreferenceHelper(this) }
    override fun onCreateInternal() {
        if (preferences.getBoolean(Constants.GAME_MANAGER_PREF_KEY)) launchBrandGamesManager()
        else launchGooglePlayGamesFolder()
    }

    private fun launchBrandGamesManager() {
        val intents = GamesBrandsPackages.gamesLaunchersIntents.map { (pkg, cls) ->
            Intent().apply {
                setClassName(pkg, cls)
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }
        launchShortcuts(intents, R.string.game_manager_not_found)
    }

    private fun launchGooglePlayGamesFolder() {
        val intent = Intent().apply {
            setClassName(
                "com.google.android.play.games",
                "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
            )
        }
        launchShortcuts(listOf(intent), R.string.play_games_not_found)
    }
}