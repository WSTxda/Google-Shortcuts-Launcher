package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.utils.IncognitoBrowsersPackages

class IncognitoShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        val intents = IncognitoBrowsersPackages.incognitoLauncherIntents.map { (pkg, cls) ->
            Intent().apply {
                setClassName(pkg, cls)
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }
        launchShortcuts(intents, R.string.browser_not_found)
    }
}