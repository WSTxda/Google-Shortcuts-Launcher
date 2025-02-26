package com.wstxda.gsl.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.wstxda.gsl.utils.ActivityUtils

abstract class ShortcutsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInternal()
        finish()
    }

    protected abstract fun onCreateInternal()

    @Suppress("SameParameterValue")
    protected fun tryStartActivity(packageName: String, className: String): Boolean {
        return ActivityUtils.tryStartActivity(this, packageName, className)
    }

    protected fun tryStartActivity(intent: Intent): Boolean {
        return ActivityUtils.tryStartActivity(this, intent)
    }

    protected fun showToast(messageResId: Int) {
        ActivityUtils.showToast(this, messageResId)
    }
}