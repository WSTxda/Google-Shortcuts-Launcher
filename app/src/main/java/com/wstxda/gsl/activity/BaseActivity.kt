package com.wstxda.gsl.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat

abstract class BaseActivity : AppCompatActivity() {

    open val edgeToEdge: Boolean = true
    open val enforceNoContrast: Boolean = true
    open val decorFitsSystemWindows: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (edgeToEdge) {
            if (enforceNoContrast && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                enableEdgeToEdge(
                    navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
                )
                window.isNavigationBarContrastEnforced = false
            } else {
                enableEdgeToEdge()
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows)
    }

    protected fun setupToolbar(toolbar: Toolbar, showBackButton: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        if (showBackButton) {
            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
}