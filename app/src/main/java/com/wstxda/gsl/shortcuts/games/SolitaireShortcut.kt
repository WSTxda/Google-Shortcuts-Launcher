package com.wstxda.gsl.shortcuts.games

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts

class SolitaireShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(listOf(createSolitaireIntent()), R.string.play_games_not_found)
    }

    private fun createSolitaireIntent() = Intent().apply {
        component = ComponentName(
            "com.google.android.play.games", "com.google.android.play.games.solitaire"
        )
    }
}