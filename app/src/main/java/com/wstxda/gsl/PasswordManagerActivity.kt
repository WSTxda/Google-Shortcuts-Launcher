@file:Suppress("DEPRECATION")

package com.wstxda.gsl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.StringRes

class PasswordManagerActivity : Activity() {

    companion object {
        private const val ROOT_COMMAND = "su"
        private const val ROOT_COMMAND_OPTIONS = "-c"
        private const val PASSWORD_MANAGER_PACKAGE = "com.google.android.gms"
        private const val PASSWORD_MANAGER_ACTIVITY = "com.google.android.gms.credential.manager.PasswordManagerActivity"
        private const val PASSWORD_URL_KEY = "password_manager_root"
    }

    private fun isRootAvailable(): Boolean {
        return try {
            val process = ProcessBuilder(ROOT_COMMAND, ROOT_COMMAND_OPTIONS, "id").start()
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun startPasswordManagerActivity() {
        try {
            ProcessBuilder(
                ROOT_COMMAND,
                ROOT_COMMAND_OPTIONS,
                "am",
                "start",
                "$PASSWORD_MANAGER_PACKAGE/$PASSWORD_MANAGER_ACTIVITY"
            ).start()
        } catch (e: Exception) {
            showError(R.string.password_manager_root_error)
        }
    }

    private fun startPasswordManagerLink(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showError(R.string.browser_not_found)
        }
    }

    private fun showError(@StringRes messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val useSuMode = sharedPreferences.getBoolean(PASSWORD_URL_KEY, false)

        if (useSuMode) {
            if (isRootAvailable()) {
                startPasswordManagerActivity()
            } else {
                showError(R.string.password_manager_root_error)
            }
        } else {
            val url = getString(R.string.password_url)
            startPasswordManagerLink(url)
        }
        finish()
    }
}