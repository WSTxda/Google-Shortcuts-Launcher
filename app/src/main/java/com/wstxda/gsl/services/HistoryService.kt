package com.wstxda.gsl.services

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

class HistoryService : ShortcutsActivity() {
    private val preferences by lazy { PreferenceHelper(this) }

    override fun onCreateInternal() {
        lifecycleScope.launch {
            if (preferences.getBoolean(Constants.SHORTCUT_ROOT_MODE_PREF_KEY)) {
                handleRootMode()
            } else {
                launchHistoryBrowser()
            }
        }
    }

    private suspend fun handleRootMode() {
        if (withContext(Dispatchers.IO) { RootChecker.isRootAvailable() }) {
            val success = withContext(Dispatchers.IO) {
                RootChecker.launchRootActivity(
                    "com.google.android.googlequicksearchbox",
                    "com.google.android.apps.search.soundsearch.history.HistoryActivity"
                )
            }
            if (!success) {
                showToast(R.string.google_not_found)
            }
        } else {
            showToast(R.string.root_access_error)
        }
    }

    private fun launchHistoryBrowser() {
        launchShortcuts(listOf(createBrowserIntent()), R.string.browser_not_found)
    }

    private fun createBrowserIntent(): Intent =
        Intent(Intent.ACTION_VIEW, "https://myactivity.google.com/myactivity?product=17".toUri())
}