package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class QuickShareShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        val intent = Intent().apply {
            component = ComponentName(
                "com.google.android.gms",
                "com.google.android.gms.nearby.sharing.settings.SendActivity"
            )
        }
        if (!tryStartActivity(intent)) {
            showToast(R.string.play_services_not_found)
        }
    }
}