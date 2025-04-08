package com.wstxda.gsl.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdgeNoContrast()

        binding.collapsingToolbar.title = getString(R.string.app_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (savedInstanceState == null) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            val navController = navHostFragment.navController
            navController.setGraph(R.navigation.nav_settings)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom * 2)
            insets
        }
    }

    private fun enableEdgeToEdgeNoContrast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT, Color.TRANSPARENT
                )
            )
            window.isNavigationBarContrastEnforced = false
        }
    }
}