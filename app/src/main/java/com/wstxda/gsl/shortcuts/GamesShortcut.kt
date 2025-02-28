package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.GamesBrandsPackages
import com.wstxda.gsl.utils.ShortcutLauncher

class GamesShortcut : ShortcutsActivity() {

    private val preferences by lazy { PreferenceHelper(this) }

    override fun onCreateInternal() {
        val useGameManager = preferences.getBoolean(Constants.GAME_MANAGER_PREF_KEY)
        if (useGameManager) {
            launchBrandGamesManager()
        } else {
            launchGooglePlayGamesFolder()
        }
    }

    private fun launchBrandGamesManager() {
        val options = GamesBrandsPackages.gamesLaunchersIntents.map { (packageName, className) ->
            ShortcutLauncher.LaunchOption(createIntent(packageName, className))
        }
        launchShortcuts(options, R.string.game_manager_not_found)
    }

    private fun launchGooglePlayGamesFolder() {
        launchShortcuts(
            listOf(
                ShortcutLauncher.LaunchOption(
                    createIntent(
                        "com.google.android.play.games",
                        "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
                    )
                )
            ), R.string.play_games_not_found
        )
    }

    private fun createIntent(packageName: String, className: String): Intent {
        return Intent().apply {
            setClassName(packageName, className)
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
    }
}