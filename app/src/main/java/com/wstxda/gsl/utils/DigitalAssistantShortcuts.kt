package com.wstxda.gsl.utils

import android.content.Context
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.shortcuts.*

object DigitalAssistantShortcuts {
    private val shortcutMap = mapOf(
        "files_shortcut" to FilesShortcut::class.java,
        "games_shortcut" to GamesShortcut::class.java,
        "lens_shortcut" to LensShortcut::class.java,
        "music_search_shortcut" to MusicSearchShortcut::class.java,
        "password_manager_shortcut" to PasswordManagerShortcut::class.java,
        "quick_share_shortcut" to QuickShareShortcut::class.java,
        "weather_shortcut" to WeatherShortcut::class.java
    )

    fun launchSelectedShortcut(context: Context) {
        val preferenceHelper = PreferenceHelper(context)
        val selectedShortcut =
            preferenceHelper.getString(Constants.DIGITAL_ASSISTANT_SHORTCUT_PREF_KEY, null) ?: return
        val activityClass = shortcutMap[selectedShortcut]
        if (activityClass != null) {
            val intent = Intent(context, activityClass)
            if (!context.launchShortcuts(intent)) context.showToast(R.string.digital_assistant_shortcut_disabled)
        } else {
            context.showToast(R.string.digital_assistant_shortcut_invalid)
        }
    }
}