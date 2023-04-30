package com.wstxda.gsl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.widget.Toast

@SuppressLint("Registered")
class GameActivity : Activity() {
    // android.app.Activity
    public override fun onResume() {
        super.onResume()
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.addCategory("android.intent.category.LAUNCHER")
            intent.component = ComponentName(
                "com.google.android.play.games",
                "com.google.android.apps.play.games.features.gamefolder.GameFolderTrampolineActivity"
            )
            startActivity(intent)
            finish()
        } catch (unused: Exception) {
            Toast.makeText(
                applicationContext,
                R.string.activity_not_found_games,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}