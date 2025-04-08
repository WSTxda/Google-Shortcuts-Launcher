package com.wstxda.gsl.logic

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes

object ActivityUtils {

    fun tryStartActivity(context: Context, intent: Intent): Boolean {
        return runCatching {
            context.startActivity(intent)
            true
        }.getOrDefault(false)
    }

//    fun tryStartActivity(context: Context, packageName: String, className: String): Boolean {
//        val intent = Intent().apply {
//            action = Intent.ACTION_MAIN
//            addCategory(Intent.CATEGORY_LAUNCHER)
//            component = ComponentName(packageName, className)
//        }
//        return tryStartActivity(context, intent)
//    }

    fun showToast(context: Context, @StringRes messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show()
    }
}