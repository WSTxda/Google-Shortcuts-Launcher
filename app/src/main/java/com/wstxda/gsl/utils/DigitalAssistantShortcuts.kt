package com.wstxda.gsl.utils

import android.content.Context
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.logic.ActivityUtils
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.logic.ShortcutLauncher
import com.wstxda.gsl.shortcuts.*

object DigitalAssistantShortcuts {
    private val ShortcutsArrays = mapOf(
//        "assistant_shortcut" to AssistantShortcut::class.java,
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
            preferenceHelper.getString("digital_assistant_shortcut", null) ?: return

        val activityClass = ShortcutsArrays[selectedShortcut]
        if (activityClass != null) {
            val intent = Intent(context, activityClass)
            val launcher = ShortcutLauncher(context)
            val success = launcher.launch(ShortcutLauncher.LaunchOption(intent))
            if (!success) {
                ActivityUtils.showToast(context, R.string.digital_assistant_shortcut_disabled)
            }
        } else {
            ActivityUtils.showToast(context, R.string.digital_assistant_shortcut_invalid)
        }
    }
}