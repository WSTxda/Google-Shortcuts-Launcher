package com.wstxda.gsl.utils

import android.net.Uri
import androidx.core.net.toUri

object Constants {

    // -------------------------------------------------------------------------
    // Preferences keys — digital assistant
    // -------------------------------------------------------------------------

    const val DIGITAL_ASSISTANT_SETUP_PREF_KEY = "digital_assistant_setup"
    const val DIGITAL_ASSISTANT_SHORTCUT_PREF_KEY = "digital_assistant_shortcut"

    // -------------------------------------------------------------------------
    // Preferences keys — shortcuts settings
    // -------------------------------------------------------------------------

    const val ADD_TILE_SHORTCUT_PREF_KEY = "add_shortcut_tile"
    const val TILE_SHORTCUT_PREF_KEY = "tile_shortcut"
    const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val SHORTCUT_ROOT_MODE_PREF_KEY = "shortcut_root_mode"

    // -------------------------------------------------------------------------
    // Preferences keys — others
    // -------------------------------------------------------------------------

    const val THEME_PREF_KEY = "select_theme"
    const val LIBRARY_PREF_KEY = "library"

    // -------------------------------------------------------------------------
    // Theme values
    // -------------------------------------------------------------------------

    const val THEME_SYSTEM = "system"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"

    // -------------------------------------------------------------------------
    // Dialog / fragment tags
    // -------------------------------------------------------------------------

    const val DIGITAL_ASSISTANT_DIALOG = "DigitalAssistantSetupDialog"

    // -------------------------------------------------------------------------
    // SharedPreferences
    // -------------------------------------------------------------------------

    const val IS_ASSIST_SETUP_DONE = "is_assist_setup_done"

    // -------------------------------------------------------------------------
    // Logs tags
    // -------------------------------------------------------------------------

    const val ROOT_CHECKER = "RootChecker"

    // -------------------------------------------------------------------------
    // Updater GitHub API
    // -------------------------------------------------------------------------

    const val GITHUB_RELEASE_URL = "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"
    const val GITHUB_RELEASE_PAGE_URL = "https://github.com/WSTxda/Google-Shortcuts-Launcher/releases/latest"

    // -------------------------------------------------------------------------
    // Storage URI patch
    // -------------------------------------------------------------------------

    val STORAGE_URI: Uri = "content://com.android.externalstorage.documents/root/primary".toUri()
}