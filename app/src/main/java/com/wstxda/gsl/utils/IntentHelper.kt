package com.wstxda.gsl.utils

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes

object IntentHelper {

    private const val TAG = "IntentHelper"

    fun tryStartActivity(context: Context, packageName: String, className: String): Boolean {
        val intent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(packageName, className)
        }
        return tryStartActivity(context, intent)
    }

    fun tryStartActivity(context: Context, intent: Intent): Boolean {
        return try {
            context.startActivity(intent)
            true
        } catch (_: ActivityNotFoundException) {
            Log.d(TAG, "Activity not found: ${intent.component}")
            false
        } catch (e: Exception) {
            Log.w(TAG, "Failed to start activity: ${intent.component}", e)
            false
        }
    }

    fun showToast(context: Context, @StringRes messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show()
    }
}