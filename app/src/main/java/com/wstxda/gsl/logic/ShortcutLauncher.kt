package com.wstxda.gsl.logic

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.launchShortcuts(intents: List<Intent>, errorMessageResId: Int): Boolean {
    intents.forEach { intent ->
        if (launchShortcuts(intent)) return true
    }
    showToast(errorMessageResId)
    return false
}

fun Context.launchShortcuts(intent: Intent): Boolean = runCatching {
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    true
}.getOrElse { false }

fun Context.launchShortcutsHistory(intent: Intent): Boolean = runCatching {
    // TODO: Investigate why FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY is required for Lens to work properly.
    // Currently, using this flag causes an issue where launching another shortcut after (e.g., FilesShortcut) reopens Lens instead of the another shortcut.
    intent.flags = Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
    startActivity(intent)
    true
}.getOrElse { false }

fun Context.showToast(messageResId: Int) {
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
}