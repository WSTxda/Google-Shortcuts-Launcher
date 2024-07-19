package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class GameActivity : Activity() {

    private val prefsName = "MyPrefsFile"
    private val prefsKeyDialogShown = "dialogShown"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndHandleDialog()
    }

    private fun checkAndHandleDialog() {
        val dialogShown = getSharedPreferences(prefsName, 0).getBoolean(prefsKeyDialogShown, false)

        if (!dialogShown) {
            showAssistantDialog()
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

    private fun showAssistantDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.assistant_dialog_title)
            setMessage(R.string.assistant_dialog_summary)
            setPositiveButton(R.string.assistant_dialog_setup) { _, _ ->
                openAssistantSettings()
                markDialogAsShown()
                finish()
            }.setNegativeButton(R.string.assistant_dialog_cancel) { _, _ ->
                markDialogAsShown()
                finish()
            }.setOnCancelListener {
                finish()
            }.show()
        }
    }

    private fun markDialogAsShown() {
        getSharedPreferences(prefsName, 0).edit().putBoolean(prefsKeyDialogShown, true).apply()
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