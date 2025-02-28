package com.wstxda.gsl.logic

object RootChecker {

    fun isRootAvailable(): Boolean = runCatching {
        val process = ProcessBuilder("su", "-c", "id").start()
        process.waitFor() == 0
    }.getOrDefault(false)

    fun launchRootActivity(packageName: String, className: String): Boolean {
        return runCatching {
            ProcessBuilder(
                "su", "-c", "am", "start", "$packageName/$className"
            ).start()
            true
        }.getOrDefault(false)
    }
}