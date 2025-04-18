package com.wstxda.gsl.activity

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivitySettingsBinding
import com.wstxda.gsl.fragments.preferences.ThemePreferences
import com.wstxda.gsl.ui.ThemeManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val themePreferences = ThemePreferences(newBase!!)
        val savedTheme = runBlocking { themePreferences.themeFlow.first() }
        ThemeManager.applyTheme(savedTheme)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdgeNoContrast()

        binding.collapsingToolbar.title = getString(R.string.app_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostContainer) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom * 2
            )
            insets
        }
    }

    private fun enableEdgeToEdgeNoContrast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
            )
            window.isNavigationBarContrastEnforced = false
        }
    }
}