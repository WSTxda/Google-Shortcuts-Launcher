package com.wstxda.gsl.logic

import java.io.DataOutputStream

object RootChecker {
    fun isRootAvailable(): Boolean = runCatching {
        val process = Runtime.getRuntime().exec("su")
        process.waitFor()
        process.exitValue() == 0
    }.getOrElse { false }

    fun launchRootActivity(packageName: String, activityName: String): Boolean = runCatching {
        val process = Runtime.getRuntime().exec("su")
        DataOutputStream(process.outputStream).use { os ->
            os.writeBytes("am start -n $packageName/$activityName\n")
            os.writeBytes("exit\n")
            os.flush()
        }
        process.waitFor()
        process.exitValue() == 0
    }.getOrElse { false }
}