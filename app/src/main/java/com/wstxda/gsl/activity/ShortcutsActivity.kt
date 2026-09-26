package com.wstxda.gsl.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

abstract class ShortcutsActivity : BaseActivity() {

    private var launchJob: Job? = null
    private var launchAtMillis = 0L
    private var launchStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState?.getBoolean(Constants.STATE_LAUNCH_STARTED) == true) {
            finish()
            return
        }

        val delayMillis = PreferenceHelper(this).getShortcutLaunchDelayMillis()
        launchAtMillis = savedInstanceState?.getLong(Constants.STATE_LAUNCH_AT)
            ?: (SystemClock.uptimeMillis() + delayMillis)

        if (delayMillis == 0L) {
            launchAndFinish()
            return
        }

        launchJob = lifecycleScope.launch {
            delay((launchAtMillis - SystemClock.uptimeMillis()).coerceAtLeast(0L).milliseconds)
            lifecycle.withResumed { launchAndFinish() }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(Constants.STATE_LAUNCH_AT, launchAtMillis)
        outState.putBoolean(Constants.STATE_LAUNCH_STARTED, launchStarted)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        launchJob?.cancel()
        if (!isChangingConfigurations) finish()
        super.onStop()
    }

    private fun launchAndFinish() {
        if (launchStarted || isFinishing) return
        launchStarted = true
        onCreateInternal()
        finish()
    }

    abstract fun onCreateInternal()
}