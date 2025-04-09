package com.wstxda.gsl.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupEdgeToEdge()
        setupToolbar()
        setupNavigation(savedInstanceState)
        setupWindowInsets()
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }

    private fun setupToolbar() {
        binding.collapsingToolbar.title = getString(R.string.app_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            (supportFragmentManager.findFragmentById(R.id.nav_host_container) as? NavHostFragment)?.navController?.setGraph(
                R.navigation.nav_settings
            )
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
    }
}