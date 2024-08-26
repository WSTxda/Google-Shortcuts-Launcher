package com.wstxda.gsl

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class GameActivity : AppCompatActivity() {

    companion object {
        private const val GAME_MANAGER_PREF_KEY = "use_game_manager"
        private const val PLAY_GAMES_PACKAGE_NAME = "com.google.android.play.games"
        private const val PLAY_GAMES_CLASS_NAME = "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndHandleBottomSheetDialog()
    }

    private fun checkAndHandleBottomSheetDialog() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bottomSheetShown = sharedPreferences.getBoolean("sheetShown", false)

        if (!bottomSheetShown) {
            showAssistantBottomSheet()
        } else {
            launchGameManagerBrands()
        }
    }

    private fun launchGameManagerBrands() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val useGameManager = sharedPreferences.getBoolean(GAME_MANAGER_PREF_KEY, false)

        if (useGameManager) {
            val gameManager = GameManagerBrands(this)
            if (!gameManager.launchGameManager()) {
                showGameManagerNotFoundMessage()
            }
        } else {
            launchGooglePlayGamesFolder()
        }
        finish()
    }

    private fun launchGooglePlayGamesFolder() {
        val playGamesIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(PLAY_GAMES_PACKAGE_NAME, PLAY_GAMES_CLASS_NAME)
        }

        if (!tryStartActivity(playGamesIntent)) {
            showPlayGamesNotFoundMessage()
        }
    }

    private fun tryStartActivity(intent: Intent): Boolean {
        return try {
            startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    @SuppressLint("InflateParams")
    private fun showAssistantBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_assistant, null, false)

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<Button>(R.id.setup_button).setOnClickListener {
            openAssistantSettings()
            markDialogAsShown()
            bottomSheetDialog.dismiss()
            finish()
        }

        bottomSheetView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            markDialogAsShown()
            bottomSheetDialog.dismiss()
            finish()
        }

        bottomSheetDialog.setOnCancelListener {
            finish()
        }

        bottomSheetDialog.show()
    }

    private fun markDialogAsShown() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("sheetShown", true)
            .apply()
    }

    private fun openAssistantSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun showPlayGamesNotFoundMessage() {
        Toast.makeText(applicationContext, R.string.play_games_not_found, Toast.LENGTH_SHORT).show()
    }

    private fun showGameManagerNotFoundMessage() {
        Toast.makeText(applicationContext, R.string.game_manager_not_found, Toast.LENGTH_SHORT)
            .show()
    }
}