package com.wstxda.gsl.utils

import android.net.Uri

object Constants {
    const val GAME_MANAGER_PREF_KEY = "device_game_manager"
    const val PASSWORD_URL_KEY = "password_manager_root"
    const val PASSWORD_ACTIVITY = "com.google.android.gms.credential.manager.PasswordManagerActivity"
    const val PASSWORD_PACKAGE = "com.google.android.gms"
    val STORAGE_URI: Uri = Uri.parse("content://com.android.externalstorage.documents/root/primary")
}