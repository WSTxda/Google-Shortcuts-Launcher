package com.wstxda.gsl

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

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
            handleActivityFlow()
        }
    }

    private fun handleActivityFlow() {
        if (!tryOpenActivity(
                "com.miui.securitycenter", "com.miui.gamebooster.ui.GameBoosterMainActivity"
            )
        ) {
            openPlayGames()
        }
        finish()
    }

    private fun showAssistantDialog() {
        AlertDialog.Builder(this).setTitle(R.string.assistant_dialog_title)
            .setMessage(R.string.assistant_dialog_summary)
            .setPositiveButton(R.string.assistant_dialog_setup) { _, _ ->
                openAssistantSettings()
                dialogHide()
                finish()
            }.setNegativeButton(R.string.assistant_dialog_cancel) { _, _ ->
                dialogHideHandle()
                finish()
            }.setOnCancelListener {
                finish()
            }.show()
    }

    private fun dialogHideHandle() {
        getSharedPreferences(prefsName, 0).edit().putBoolean(prefsKeyDialogShown, true).apply()
        handleActivityFlow()
    }

    private fun dialogHide() {
        getSharedPreferences(prefsName, 0).edit().putBoolean(prefsKeyDialogShown, true).apply()
    }

    private fun openAssistantSettings() {
        startActivity(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    private fun tryOpenActivity(packageName: String, className: String): Boolean {
        return try {
            startActivity(Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                component = ComponentName(packageName, className)
            })
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun openPlayGames() {
        val playGamesPackageName = "com.google.android.play.games"
        val playGamesClassName =
            "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"

        val playGamesIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(playGamesPackageName, playGamesClassName)
        }

        if (tryStartActivity(playGamesIntent)) {
            return
        }

        showPlayGamesNotFoundMessage()
    }

    private fun tryStartActivity(intent: Intent): Boolean {
        return try {
            startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun showPlayGamesNotFoundMessage() {
        Toast.makeText(applicationContext, R.string.play_games_not_found, Toast.LENGTH_SHORT).show()
    }
}