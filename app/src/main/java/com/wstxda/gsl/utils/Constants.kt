package com.wstxda.gsl.utils

import android.net.Uri
import androidx.core.net.toUri

object Constants {
    const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val PASSWORD_URL_KEY = "password_manager_root"
    const val PASSWORD_ACTIVITY = "com.google.android.gms.credential.manager.PasswordManagerActivity"
    const val PASSWORD_PACKAGE = "com.google.android.gms"
    const val MUSIC_SEARCH_TILE_KEY = "add_music_search_tile"
    const val DIGITAL_ASSISTANT_SETUP_KEY = "digital_assistant_setup"
    const val DIGITAL_ASSISTANT_DIALOG_TAG = "DigitalAssistantSetupDialog"
    const val GITHUB_RELEASE_URL = "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"
    const val THEME_PREF_KEY = "select_theme"
    const val THEME_SYSTEM = "system"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"
    val STORAGE_URI: Uri = "content://com.android.externalstorage.documents/root/primary".toUri()
}