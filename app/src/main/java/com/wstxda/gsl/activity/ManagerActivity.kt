package com.wstxda.gsl.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.wstxda.gsl.R
import com.wstxda.gsl.databinding.ActivityManagerBinding
import com.wstxda.gsl.services.UpdaterService
import com.wstxda.gsl.ui.component.FreeAndroidWarnDialog

class ManagerActivity : BaseActivity() {

    private val binding by lazy { ActivityManagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        applySystemBarInsets(binding.navHostContainer)

        setupToolbar(binding.toolbar, showBackButton = false)
        binding.collapsingToolbar.title = getString(R.string.app_manager_shortcuts)

        FreeAndroidWarnDialog.show(supportFragmentManager, this)
        UpdaterService.checkForUpdatesAuto(lifecycleScope, this, supportFragmentManager)
    }
}