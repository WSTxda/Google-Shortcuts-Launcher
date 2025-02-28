package com.wstxda.gsl.ui

import android.os.Bundle
import androidx.annotation.StringRes
import com.wstxda.gsl.logic.ActivityUtils
import com.wstxda.gsl.utils.ShortcutLauncher

abstract class ShortcutsActivity : androidx.activity.ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInternal()
    }

    abstract fun onCreateInternal()

    protected fun launchShortcuts(
        options: List<ShortcutLauncher.LaunchOption>, @StringRes errorMessage: Int
    ) {
        val launcher = ShortcutLauncher(this)
        val success = launcher.launch(*options.toTypedArray())
        if (!success) {
            ActivityUtils.showToast(this, errorMessage)
        }
        finish()
    }

    protected fun showToast(@StringRes messageResId: Int) {
        ActivityUtils.showToast(this, messageResId)
    }
}