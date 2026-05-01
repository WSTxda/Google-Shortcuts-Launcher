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

    const val SHORTCUT_ADD_TILE_PREF_KEY = "shortcut_add_tile"
    const val SHORTCUT_TILE_PREF_KEY = "shortcut_tile"
    const val DEVICE_GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val SHORTCUT_ROOT_MODE_PREF_KEY = "shortcut_root_mode"

    // -------------------------------------------------------------------------
    // Preferences keys — others
    // -------------------------------------------------------------------------

    const val THEME_PREF_KEY = "select_theme"

    // -------------------------------------------------------------------------
    // Preferences keys — about
    // -------------------------------------------------------------------------

    const val LIBRARY_PREF_KEY = "library"
    const val UPDATER_PREF_KEY = "updater"

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
    const val FREE_ANDROID_WARN_DIALOG = "FreeAndroidWarnDialog"
    const val UPDATER_DIALOG = "UpdaterBottomSheet"

    // -------------------------------------------------------------------------
    // SharedPreferences
    // -------------------------------------------------------------------------

    const val IS_ASSIST_SETUP_DONE = "is_assist_setup_done"
    const val IS_WARN_DISMISSED = "is_warn_dismissed"

    // -------------------------------------------------------------------------
    // Logs tags
    // -------------------------------------------------------------------------

    const val ROOT_CHECKER = "RootChecker"

    // -------------------------------------------------------------------------
    // Updater GitHub API
    // -------------------------------------------------------------------------

    const val GITHUB_TITLE = "title"
    const val GITHUB_VERSION = "version"
    const val GITHUB_CHANGELOG = "changelog"
    const val GITHUB_DOWNLOAD_URL = "download_url"
    const val GITHUB_PAGE_URL = "page_url"
    const val GITHUB_UPDATE_CHECKED = "update_checked"

    const val GITHUB_API_URL = "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"
    const val GITHUB_RELEASE_URL = "https://github.com/WSTxda/Google-Shortcuts-Launcher/releases/latest"

    // -------------------------------------------------------------------------
    // Storage URI patch
    // -------------------------------------------------------------------------

    val STORAGE_URI: Uri = "content://com.android.externalstorage.documents/root/primary".toUri()
}