package com.wstxda.gsl.activity

import android.os.Bundle

abstract class ShortcutsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInternal()
        finish()
    }

    abstract fun onCreateInternal()
}