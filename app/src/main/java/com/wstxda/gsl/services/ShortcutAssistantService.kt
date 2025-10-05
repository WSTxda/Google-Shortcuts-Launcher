package com.wstxda.gsl.services

import android.content.Context
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.ShortcutsMap.shortcuts

class ShortcutAssistantService : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchAssistantShortcut(this)
    }

    private fun launchAssistantShortcut(context: Context) {
        val preferenceHelper = PreferenceHelper(context)
        val selectedShortcut =
            preferenceHelper.getString(Constants.DIGITAL_ASSISTANT_SHORTCUT_PREF_KEY, null)
                ?: return
        val activityClass = shortcuts[selectedShortcut]
        if (activityClass != null) {
            val intent = Intent(context, activityClass)
            if (!context.launchShortcuts(intent)) context.showToast(R.string.shortcut_disabled)
        } else {
            context.showToast(R.string.shortcut_invalid)
        }
    }
}