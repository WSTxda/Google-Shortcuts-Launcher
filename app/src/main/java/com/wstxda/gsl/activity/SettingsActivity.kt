package com.wstxda.gsl.activity

import android.os.Bundle
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        applySystemBarInsets(binding.navHostContainer)

        setupToolbar(binding.toolbar, showBackButton = false)
        binding.collapsingToolbar.title = getString(R.string.app_settings)
    }
}