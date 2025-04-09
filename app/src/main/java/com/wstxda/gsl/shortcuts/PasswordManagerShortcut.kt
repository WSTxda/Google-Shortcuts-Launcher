package com.wstxda.gsl.shortcuts

import android.content.Intent
import androidx.core.net.toUri
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.RootChecker
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordManagerShortcut : ShortcutsActivity() {
    private val preferences by lazy { PreferenceHelper(this) }
    override fun onCreateInternal() {
        val useSuMode = preferences.getBoolean(Constants.PASSWORD_URL_KEY)
        if (useSuMode) {
            CoroutineScope(Dispatchers.Main).launch {
                if (RootChecker.isRootAvailable()) {
                    if (!RootChecker.launchRootActivity(
                            Constants.PASSWORD_PACKAGE, Constants.PASSWORD_ACTIVITY
                        )
                    ) {
                        showToast(R.string.play_services_not_found)
                    }
                } else {
                    showToast(R.string.password_manager_root_error)
                }
            }
        } else {
            launchShortcuts(listOf(createBrowserIntent()), R.string.browser_not_found)
        }
    }

    private fun createBrowserIntent() =
        Intent(Intent.ACTION_VIEW, getString(R.string.password_url).toUri())
}