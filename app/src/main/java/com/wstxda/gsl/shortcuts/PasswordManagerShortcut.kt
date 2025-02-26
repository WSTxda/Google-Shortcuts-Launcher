package com.wstxda.gsl.shortcuts

import android.content.Intent
import android.net.Uri
import androidx.preference.PreferenceManager
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class PasswordManagerShortcut : ShortcutsActivity() {

    companion object {
        private const val PASSWORD_URL_KEY = "password_manager_root"
    }

    override fun onCreateInternal() {
        val useSuMode =
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PASSWORD_URL_KEY, false)
        when {
            useSuMode && isRootAvailable() -> launchPasswordManager()
            useSuMode -> showToast(R.string.password_manager_root_error)
            else -> startPasswordManagerBrowser(getString(R.string.password_url))
        }
    }

    private fun isRootAvailable(): Boolean = runCatching {
        val process = ProcessBuilder("su", "-c", "id").start()
        process.waitFor() == 0
    }.getOrDefault(false)

    private fun launchPasswordManager() {
        runCatching {
            ProcessBuilder(
                "su",
                "-c",
                "am",
                "start",
                "com.google.android.gms/com.google.android.gms.credential.manager.PasswordManagerActivity"
            ).start()
        }.onFailure {
            showToast(R.string.password_manager_root_error)
        }
    }

    private fun startPasswordManagerBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (!tryStartActivity(intent)) {
            showToast(R.string.browser_not_found)
        }
    }
}