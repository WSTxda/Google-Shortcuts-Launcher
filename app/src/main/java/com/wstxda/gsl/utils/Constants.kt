package com.wstxda.gsl.utils

import android.net.Uri
import androidx.core.net.toUri

object Constants {
    const val DIGITAL_ASSISTANT_SETUP_PREF_KEY = "digital_assistant_setup"
    const val DIGITAL_ASSISTANT_SHORTCUT_PREF_KEY = "digital_assistant_shortcut"
    const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val PASSWORD_MANAGER_ROOT_PREF_KEY = "password_manager_root"
    const val LIBRARY_PREF_KEY = "library"
    const val THEME_PREF_KEY = "select_theme"

    const val THEME_SYSTEM = "system"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"

    const val MUSIC_SEARCH_TILE_KEY = "add_music_search_tile"

    const val DIGITAL_ASSISTANT_DIALOG = "DigitalAssistantSetupDialog"
    const val IS_ASSIST_SETUP_DONE = "is_assist_setup_done"

    const val GITHUB_RELEASE_URL = "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"

    val STORAGE_URI: Uri = "content://com.android.externalstorage.documents/root/primary".toUri()
}