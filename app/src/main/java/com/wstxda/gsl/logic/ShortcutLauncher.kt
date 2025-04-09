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

fun Context.showToast(messageResId: Int) {
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
}