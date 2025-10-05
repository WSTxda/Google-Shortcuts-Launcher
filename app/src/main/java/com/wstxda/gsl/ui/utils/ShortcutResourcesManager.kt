package com.wstxda.gsl.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import com.wstxda.gsl.R
import java.util.Locale

class ShortcutResourcesManager(private val context: Context) {

    @SuppressLint("DiscouragedApi")
    fun getShortcutIcon(shortcutValue: String?): Int {
        if (shortcutValue.isNullOrEmpty()) return R.drawable.ic_shortcut_default

        val resourceName = "ic_shortcut_" + shortcutValue.replace("_shortcut", "")
        val resId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)

        return if (resId != 0) resId else R.drawable.ic_shortcut_default
    }

    @SuppressLint("DiscouragedApi")
    fun getShortcutName(shortcutValue: String?): String {
        if (shortcutValue.isNullOrEmpty()) {
            return context.getString(R.string.app_name)
        }

        val resourceName = shortcutValue.replace("_shortcut", "")
        val stringResId =
            context.resources.getIdentifier(resourceName, "string", context.packageName)

        return if (stringResId != 0) {
            context.getString(stringResId)
        } else {
            resourceName.replace("_", " ").capitalizeWords()
        }
    }

    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
        it.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(
                Locale.getDefault()
            ) else char.toString()
        }
    }
}