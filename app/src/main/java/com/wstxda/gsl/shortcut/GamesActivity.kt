package com.wstxda.gsl.shortcut

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.GamesBrandsPackages
import com.wstxda.gsl.utils.IntentHelper

class GamesActivity : AppCompatActivity() {

    companion object {
        private const val GAME_MANAGER_PREF_KEY = "device_game_manager"
        private const val SHEET_SHOWN_PREF_KEY = "sheetShown"
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndHandleBottomSheetDialog()
    }

    private fun checkAndHandleBottomSheetDialog() {
        if (!sharedPreferences.getBoolean(SHEET_SHOWN_PREF_KEY, false)) {
            showAssistantBottomSheet()
        } else {
            launchGames()
        }
    }

    private fun launchGames() {
        val useGameManager = sharedPreferences.getBoolean(GAME_MANAGER_PREF_KEY, false)

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
            this,
            "com.google.android.play.games",
            "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
        )
    }

    private fun showAssistantBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(
            R.layout.bottom_sheet_assistant, findViewById(android.R.id.content), false
        ) ?: return

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<Button>(R.id.setup_button).setOnClickListener {
            openAssistantSettings()
            markSheetAsShown()
            bottomSheetDialog.dismiss()
            finish()
        }

        bottomSheetView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            markSheetAsShown()
            bottomSheetDialog.dismiss()
            launchGames()
        }

        bottomSheetDialog.setOnCancelListener {
            finish()
        }

        bottomSheetDialog.show()
    }

    private fun openAssistantSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun markSheetAsShown() {
        sharedPreferences.edit().putBoolean(SHEET_SHOWN_PREF_KEY, true).apply()
    }
}