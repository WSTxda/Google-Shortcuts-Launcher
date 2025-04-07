package com.wstxda.gsl.utils

import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.wstxda.gsl.shortcuts.AssistantShortcut
import com.wstxda.gsl.shortcuts.FilesShortcut
import com.wstxda.gsl.shortcuts.GamesShortcut
import com.wstxda.gsl.shortcuts.LensShortcut
import com.wstxda.gsl.shortcuts.MusicSearchShortcut
import com.wstxda.gsl.shortcuts.PasswordManagerShortcut
import com.wstxda.gsl.shortcuts.QuickShareShortcut
import com.wstxda.gsl.shortcuts.WeatherShortcut
import kotlin.collections.get

object DigitalAssistantShortcut {
    private val ShortcutsArrays = mapOf(
        "assistant_shortcut" to AssistantShortcut::class.java,
        "files_shortcut" to FilesShortcut::class.java,
        "games_shortcut" to GamesShortcut::class.java,
        "lens_shortcut" to LensShortcut::class.java,
        "music_search_shortcut" to MusicSearchShortcut::class.java,
        "password_manager_shortcut" to PasswordManagerShortcut::class.java,
        "quick_share_shortcut" to QuickShareShortcut::class.java,
        "weather_shortcut" to WeatherShortcut::class.java
    )

    private fun getActivityClass(key: String?): Class<*>? = ShortcutsArrays[key]

    fun launchSelectedShortcut(context: Context) {
        val selectedShortcut = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("digital_assistant_shortcut", null) ?: return

        getActivityClass(selectedShortcut)?.let { activityClass ->
            context.startActivity(Intent(context, activityClass))
        }
    }
}