package com.wstxda.gsl.shortcut

import android.content.ComponentName
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShizukuShortcut

class PasswordManagerShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShizukuShortcut(
            createPasswordManagerIntent(), R.string.play_services_not_found
        )
    }

    private fun createPasswordManagerIntent() = ComponentName(
        "com.google.android.gms",
        "com.google.android.gms.credential.manager.PasswordManagerActivity"
    )
}