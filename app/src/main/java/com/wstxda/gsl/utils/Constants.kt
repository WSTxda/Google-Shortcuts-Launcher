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

    const val SHORTCUT_TILES_PREF_KEY = "shortcut_tiles"
    const val TILE_ASSISTANT_PREF_KEY = "tile_assistant"
    const val TILE_GAMES_PREF_KEY = "tile_games"
    const val TILE_LENS_PREF_KEY = "tile_lens"
    const val TILE_MUSIC_SEARCH_PREF_KEY = "tile_music_search"
    const val TILE_QUICK_SHARE_PREF_KEY = "tile_quick_share"
    const val TILE_SCANNER_PREF_KEY = "tile_scanner"
    const val TILE_SEARCH_PREF_KEY = "tile_search"
    const val SHORTCUT_LAUNCH_DELAY_PREF_KEY = "shortcut_launch_delay"
    const val DEVICE_GAME_MANAGER_PREF_KEY = "device_game_manager"

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
    // Shortcut launch delay
    // -------------------------------------------------------------------------

    const val SHORTCUT_LAUNCH_DELAY_MAX_MS = 1000
    const val SHORTCUT_LAUNCH_DELAY_STEP_MS = 100

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

    const val STATE_LAUNCH_AT = "shortcut_launch_at"
    const val STATE_LAUNCH_STARTED = "shortcut_launch_started"
    const val STATE_SHIZUKU_SETUP_SHOWN = "shizuku_setup_shown"
    const val STATE_SHIZUKU_LAUNCH_STARTED = "shizuku_launch_started"
    const val STATE_SHIZUKU_PERMISSION_INVALIDATED = "shizuku_permission_invalidated"

    // -------------------------------------------------------------------------
    // Shizuku
    // -------------------------------------------------------------------------

    const val SHIZUKU_PACKAGE = "moe.shizuku.privileged.api, "
    const val SHIZUKU_PERMISSION_REQUEST_CODE = 1
    const val SHIZUKU_SERVICE_PROCESS = "shizuku"
    const val SHIZUKU_SERVICE_TAG = "gsl_shizuku"
    const val SHIZUKU_SERVICE_VERSION = 1
    const val EXTRA_SHIZUKU_TARGET_COMPONENT = "shizuku_target_component"
    const val EXTRA_SHIZUKU_ERROR_MESSAGE = "shizuku_error_message"
    const val SHIZUKU_DOWNLOAD_URL = "https://shizuku.rikka.app/download/"

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