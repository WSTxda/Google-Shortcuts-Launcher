package com.wstxda.gsl.shortcuts

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.RootChecker
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordManagerShortcut : ShortcutsActivity() {
    private val preferences by lazy { PreferenceHelper(this) }

    override fun onCreateInternal() {
        lifecycleScope.launch {
            when {
                preferences.getBoolean(Constants.PASSWORD_MANAGER_ROOT_PREF_KEY) -> handleRootMode()
                else -> launchPasswordManagerBrowser()
            }
        }
    }

    private suspend fun handleRootMode() {
        if (withContext(Dispatchers.IO) { RootChecker.isRootAvailable() }) {
            val success = withContext(Dispatchers.IO) {
                RootChecker.launchRootActivity(
                    "com.google.android.gms",
                    "com.google.android.gms.credential.manager.PasswordManagerActivity"
                )
            }
            if (!success) {
                showToast(R.string.play_services_not_found)
            }
        } else {
            showToast(R.string.root_access_error)
        }
    }

    private fun launchPasswordManagerBrowser() {
        launchShortcuts(listOf(createBrowserIntent()), R.string.browser_not_found)
    }

    private fun createBrowserIntent(): Intent =
        Intent(Intent.ACTION_VIEW, getString(R.string.password_url).toUri())
}