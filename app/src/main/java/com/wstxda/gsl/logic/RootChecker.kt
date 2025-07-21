package com.wstxda.gsl.logic

import android.util.Log
import com.wstxda.gsl.utils.Constants
import kotlin.getOrElse
import kotlin.runCatching

object RootChecker {


    fun isRootAvailable(): Boolean = runCatching {
        val process = ProcessBuilder("su", "-c", "id").start()
        process.waitFor() == 0
    }.getOrElse {
        Log.e(Constants.ROOT_CHECKER_TAG, "Root check failed", it)
        false
    }

    fun launchRootActivity(packageName: String, activityName: String): Boolean = runCatching {
        val process =
            ProcessBuilder("su", "-c", "am", "start", "-n", "$packageName/$activityName").start()
        process.waitFor() == 0
    }.getOrElse {
        Log.e(Constants.ROOT_CHECKER_TAG, "Failed to launch root activity", it)
        false
    }
}