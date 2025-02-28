package com.wstxda.gsl.utils

import android.content.Context
import android.content.Intent
import com.wstxda.gsl.logic.ActivityUtils

class ShortcutLauncher(private val context: Context) {

    data class LaunchOption(val intent: Intent? = null)

    fun launch(vararg options: LaunchOption): Boolean {
        val validOptions = options.filter { it.intent != null }
        for (option in validOptions) {
            if (ActivityUtils.tryStartActivity(context, option.intent!!)) {
                return true
            }
        }
        return false
    }

//    fun launch(packageName: String, className: String): Boolean {
//        return ActivityUtils.tryStartActivity(context, packageName, className)
//    }
}