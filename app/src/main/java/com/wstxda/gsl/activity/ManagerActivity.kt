package com.wstxda.gsl.activity

import android.os.Bundle
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivityManagerBinding

class ManagerActivity : BaseActivity() {

    private val binding by lazy { ActivityManagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        applySystemBarInsets(binding.navHostContainer)

        setupToolbar(binding.toolbar, showBackButton = false)
        binding.collapsingToolbar.title = getString(R.string.app_manager_shortcuts)
    }
}