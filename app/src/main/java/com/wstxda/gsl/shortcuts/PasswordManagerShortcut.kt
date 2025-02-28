package com.wstxda.gsl.shortcuts

import android.content.Intent
import android.net.Uri
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.RootChecker
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.ShortcutLauncher

class PasswordManagerShortcut : ShortcutsActivity() {

    private val preferences by lazy { PreferenceHelper(this) }

    override fun onCreateInternal() {
        val useSuMode = preferences.getBoolean(Constants.PASSWORD_URL_KEY)
        when {
            useSuMode && RootChecker.isRootAvailable() -> launchPasswordManager()
            useSuMode -> showToast(R.string.password_manager_root_error)
            else -> launchPasswordManagerBrowser()
        }
    }

    private fun launchPasswordManager() {
        if (!RootChecker.launchRootActivity(
                Constants.PASSWORD_PACKAGE, Constants.PASSWORD_ACTIVITY
            )
        ) {
            showToast(R.string.play_services_not_found)
        }
    }

    private fun launchPasswordManagerBrowser() {
        launchShortcuts(
            listOf(ShortcutLauncher.LaunchOption(createBrowserIntent())), R.string.browser_not_found
        )
    }

    private fun createBrowserIntent(): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.password_url)))
    }
}