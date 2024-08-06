package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog

class GameActivity : Activity() {

    private val prefsName = "MyPrefsFile"
    private val prefsKeySheetShown = "sheetShown"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndHandleBottomSheetDialog()
    }

    private fun checkAndHandleBottomSheetDialog() {
        val bottomSheetShown =
            getSharedPreferences(prefsName, 0).getBoolean(prefsKeySheetShown, false)

        if (!bottomSheetShown) {
            showAssistantBottomSheet()
        } else {
            startGameTurbo()
        }
    }

    private fun startGameTurbo() {
        if (!tryStartActivityIntent(
                "com.miui.securitycenter", "com.miui.gamebooster.ui.GameBoosterMainActivity"
            )
        ) {
            startPlayGames()
        }
        finish()
    }

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
        getSharedPreferences(prefsName, 0).edit().putBoolean(prefsKeySheetShown, true).apply()
    }

    private fun openAssistantSettings() {
        startActivity(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun tryStartActivityIntent(packageName: String, className: String): Boolean {
        val intent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(packageName, className)
        }
        return tryStartActivity(intent)
    }

    private fun startPlayGames() {
        val playGamesPackageName = "com.google.android.play.games"
        val playGamesClassName =
            "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"

        val playGamesIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(playGamesPackageName, playGamesClassName)
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

    private fun showPlayGamesNotFoundMessage() {
        Toast.makeText(applicationContext, R.string.play_games_not_found, Toast.LENGTH_SHORT).show()
    }
}