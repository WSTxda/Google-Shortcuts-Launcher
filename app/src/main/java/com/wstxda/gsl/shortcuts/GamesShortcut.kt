package com.wstxda.gsl.shortcuts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.ActivityUtils
import com.wstxda.gsl.utils.GamesBrandsPackages

class GamesShortcut : AppCompatActivity() {

    companion object {
        private const val GAME_MANAGER_PREF_KEY = "device_game_manager"
        private const val SHEET_SHOWN_PREF_KEY = "sheetShown"
    }

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndHandleBottomSheetDialog()
    }

    private fun checkAndHandleBottomSheetDialog() {
        when (sharedPreferences.getBoolean(SHEET_SHOWN_PREF_KEY, false)) {
            false -> showAssistantBottomSheet()
            true -> launchGames()
        }
    }

    private fun launchGames() {
        val useGameManager = sharedPreferences.getBoolean(GAME_MANAGER_PREF_KEY, false)
        val success = when {
            useGameManager -> launchBrandGamesManager()
            else -> launchGooglePlayGamesFolder()
        }
        if (!success) {
            ActivityUtils.showToast(
                this,
                if (useGameManager) R.string.game_manager_not_found else R.string.play_games_not_found
            )
        }
        finish()
    }

    private fun launchBrandGamesManager(): Boolean {
        return GamesBrandsPackages.gamesLaunchersIntents.any { (packageName, className) ->
            ActivityUtils.tryStartActivity(this, packageName, className)
        }
    }

    private fun launchGooglePlayGamesFolder(): Boolean {
        return ActivityUtils.tryStartActivity(
            this,
            "com.google.android.play.games",
            "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
        )
    }

    @SuppressLint("InflateParams")
    private fun showAssistantBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_assistant, null, false)
        BottomSheetDialog(this).apply {
            setContentView(bottomSheetView)
            bottomSheetView.findViewById<Button>(R.id.setup_button).setOnClickListener {
                openAssistantSettings()
                markSheetAsShown()
                dismiss()
                finish()
            }
            bottomSheetView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                markSheetAsShown()
                dismiss()
                launchGames()
            }
            setOnCancelListener { finish() }
            show()
        }
    }

    private fun openAssistantSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun markSheetAsShown() {
        sharedPreferences.edit().putBoolean(SHEET_SHOWN_PREF_KEY, true).apply()
    }
}