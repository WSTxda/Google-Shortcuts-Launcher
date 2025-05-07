package com.wstxda.gsl.utils

import android.net.Uri
import androidx.core.net.toUri

object Constants {
    const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val PASSWORD_URL_KEY = "password_manager_root"
    const val MUSIC_SEARCH_TILE_KEY = "add_music_search_tile"
    const val DIGITAL_ASSISTANT_SETUP = "digital_assistant_setup"
    const val DIGITAL_ASSISTANT_SHORTCUT = "digital_assistant_shortcut"

    const val THEME_PREF_KEY = "select_theme"
    const val THEME_SYSTEM = "system"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"

    const val DIGITAL_ASSISTANT_DIALOG = "DigitalAssistantSetupDialog"
    const val IS_ASSIST_SETUP_DONE = "is_assist_setup_done"

    const val GITHUB_RELEASE_URL = "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"

    val STORAGE_URI: Uri = "content://com.android.externalstorage.documents/root/primary".toUri()
}