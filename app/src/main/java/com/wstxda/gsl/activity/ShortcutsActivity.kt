package com.wstxda.gsl.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ShortcutsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInternal()
        finish()
    }

    abstract fun onCreateInternal()
}