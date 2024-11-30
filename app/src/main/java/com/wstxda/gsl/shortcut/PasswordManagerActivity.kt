package com.wstxda.gsl.shortcut

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.annotation.StringRes
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.IntentHelper

class PasswordManagerActivity : Activity() {

    companion object {
        private const val ROOT_COMMAND = "su"
        private const val ROOT_COMMAND_OPTIONS = "-c"
        private const val PASSWORD_MANAGER_PACKAGE = "com.google.android.gms"
        private const val PASSWORD_MANAGER_ACTIVITY = "com.google.android.gms.credential.manager.PasswordManagerActivity"
        private const val PASSWORD_URL_KEY = "password_manager_root"
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val useSuMode =
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PASSWORD_URL_KEY, false)

        if (useSuMode) {
            if (isRootAvailable()) {
                launchPasswordManager()
            } else {
                showError(R.string.password_manager_root_error)
            }
        } else {
            startPasswordManagerBrowser(getString(R.string.password_url))
        }

        finish()
    }

    private fun isRootAvailable() = try {
        val process = ProcessBuilder(ROOT_COMMAND, ROOT_COMMAND_OPTIONS, "id").start()
        process.waitFor() == 0
    } catch (_: Exception) {
        false
    }

    private fun launchPasswordManager() {
        runCatching {
            ProcessBuilder(
                ROOT_COMMAND,
                ROOT_COMMAND_OPTIONS,
                "am",
                "start",
                "$PASSWORD_MANAGER_PACKAGE/$PASSWORD_MANAGER_ACTIVITY"
            ).start()
        }.onFailure { showError(R.string.password_manager_root_error) }
    }

    private fun startPasswordManagerBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (!IntentHelper.tryStartActivity(this, intent)) {
            showError(R.string.browser_not_found)
        }
    }

    private fun showError(@StringRes messageResId: Int) {
        IntentHelper.showToast(this, messageResId)
    }
}