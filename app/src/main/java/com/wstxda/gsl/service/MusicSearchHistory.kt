package com.wstxda.gsl.service

import android.content.ComponentName
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShizukuShortcut

class MusicSearchHistory : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShizukuShortcut(
            createMusicSearchHistoryIntent(), R.string.google_not_found
        )
    }

    private fun createMusicSearchHistoryIntent() = ComponentName(
        "com.google.android.googlequicksearchbox",
        "com.google.android.apps.search.soundsearch.history.HistoryActivity"
    )
}