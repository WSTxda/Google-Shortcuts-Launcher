package com.wstxda.gsl.shortcuts

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import kotlinx.coroutines.launch

class TravelShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        lifecycleScope.launch {
            launchTravelBrowser()
        }
    }

    private fun launchTravelBrowser() {
        launchShortcuts(listOf(createBrowserIntent()), R.string.browser_not_found)
    }

    private fun createBrowserIntent(): Intent =
        Intent(Intent.ACTION_VIEW, "https://www.google.com/travel".toUri())
}