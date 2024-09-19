package com.wstxda.gsl.shortcut

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wstxda.gsl.R
import com.wstxda.gsl.shortcut.utils.GamesBrandsPackages

class GamesActivity : AppCompatActivity() {

    companion object {
        private const val GAME_MANAGER_PREF_KEY = "device_game_manager"
        private const val PLAY_GAMES_PACKAGE_NAME = "com.google.android.play.games"
        private const val PLAY_GAMES_CLASS_NAME = "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
        private const val SHEET_SHOWN_PREF_KEY = "sheetShown"
        private const val TAG = "GamesActivity"
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
            launchGamesActivity()
        }
    }

    private fun launchGamesActivity() {
        val useGameDeviceManager = sharedPreferences.getBoolean(GAME_MANAGER_PREF_KEY, false)
        if (useGameDeviceManager) {
            val launchDeviceGameManager = GamesBrandsPackages(this)
            if (!launchDeviceGameManager.launchGameManager()) {
                showMessage(R.string.game_manager_not_found)
            }
        } else {
            launchGooglePlayGamesFolder()
        }
        finishActivity()
    }

    private fun launchGooglePlayGamesFolder() {
        val playGamesIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(PLAY_GAMES_PACKAGE_NAME, PLAY_GAMES_CLASS_NAME)
        }

        if (!tryStartActivity(playGamesIntent)) {
            showMessage(R.string.play_games_not_found)
        }
    }

    private fun tryStartActivity(intent: Intent): Boolean {
        return try {
            startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Activity not found: ${intent.component}", e)
            false
        }
    }

    private fun showAssistantBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(
            R.layout.bottom_sheet_assistant, findViewById(android.R.id.content), false
        ) ?: return

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<Button>(R.id.setup_button).setOnClickListener {
            openAssistantSettings()
            markDialogAsShown()
            bottomSheetDialog.dismiss()
            finishActivity()
        }

        bottomSheetView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            markDialogAsShown()
            bottomSheetDialog.dismiss()
            launchGamesActivity()
            finishActivity()
        }

        bottomSheetDialog.setOnCancelListener {
            finishActivity()
        }

        bottomSheetDialog.show()
    }

    private fun markDialogAsShown() {
        sharedPreferences.edit().putBoolean(SHEET_SHOWN_PREF_KEY, true).apply()
    }

    private fun openAssistantSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun showMessage(messageResId: Int) {
        Toast.makeText(applicationContext, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun finishActivity() {
        finish()
    }
}