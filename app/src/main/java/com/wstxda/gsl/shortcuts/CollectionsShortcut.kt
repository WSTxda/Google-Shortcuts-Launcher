package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class CollectionsShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(createCollectionsIntent()), R.string.play_store_not_found)
    }

    private fun createCollectionsIntent() = Intent().apply {
        component = ComponentName(
            "com.android.vending", "com.google.android.finsky.rubiks.cubes.activity.CubesActivity"
        )
    }
}