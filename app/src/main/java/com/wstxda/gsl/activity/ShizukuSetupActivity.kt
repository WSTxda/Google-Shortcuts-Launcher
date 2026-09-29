package com.wstxda.gsl.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.wstxda.gsl.R
import com.wstxda.gsl.data.ShizukuLaunchResult
import com.wstxda.gsl.data.ShizukuState
import com.wstxda.gsl.databinding.ActivityShizukuSetupBinding
import com.wstxda.gsl.fragment.ShizukuSetupFragment
import com.wstxda.gsl.logic.ShizukuChecker
import com.wstxda.gsl.logic.ShizukuLauncher
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.launch
import rikka.shizuku.Shizuku

class ShizukuSetupActivity : BaseActivity() {

    companion object {
        fun createIntent(
            context: Context, component: ComponentName, errorMessageResId: Int
        ): Intent = Intent(context, ShizukuSetupActivity::class.java).apply {
            putExtra(Constants.EXTRA_SHIZUKU_TARGET_COMPONENT, component.flattenToString())
            putExtra(Constants.EXTRA_SHIZUKU_ERROR_MESSAGE, errorMessageResId)
        }
    }

    private lateinit var binding: ActivityShizukuSetupBinding
    private lateinit var targetComponent: ComponentName

    private var errorMessageResId = R.string.shortcut_invalid
    private var setupShown = false
    private var launchStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = ComponentName.unflattenFromString(
            intent.getStringExtra(Constants.EXTRA_SHIZUKU_TARGET_COMPONENT).orEmpty()
        )
        if (component == null) {
            finish()
            return
        }

        targetComponent = component
        errorMessageResId = intent.getIntExtra(
            Constants.EXTRA_SHIZUKU_ERROR_MESSAGE, R.string.shortcut_invalid
        )

        val launchInterrupted =
            savedInstanceState?.getBoolean(Constants.STATE_SHIZUKU_LAUNCH_STARTED) == true
        setupShown =
            savedInstanceState?.getBoolean(Constants.STATE_SHIZUKU_SETUP_SHOWN) == true || launchInterrupted

        if (!setupShown && ShizukuChecker.getState(this) == ShizukuState.READY) {
            launchTarget()
            return
        }

        showSetup()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(Constants.STATE_SHIZUKU_SETUP_SHOWN, setupShown)
        outState.putBoolean(Constants.STATE_SHIZUKU_LAUNCH_STARTED, launchStarted)
        super.onSaveInstanceState(outState)
    }

    private fun showSetup(permissionInvalidated: Boolean = false) {
        if (!::binding.isInitialized) {
            binding = ActivityShizukuSetupBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupShown = true

            setupToolbar(binding.toolbar)
            binding.collapsingToolbar.title = getString(R.string.app_shizuku_setup)
        }

        if (supportFragmentManager.findFragmentById(binding.shizukuSetupFragmentContainer.id) == null) {
            supportFragmentManager.beginTransaction().replace(
                binding.shizukuSetupFragmentContainer.id, ShizukuSetupFragment.newInstance(
                    targetComponent, errorMessageResId, permissionInvalidated
                )
            ).commit()
        }
    }

    private fun launchTarget() {
        if (launchStarted) return

        launchStarted = true
        lifecycleScope.launch {
            when (ShizukuLauncher.launch(this@ShizukuSetupActivity, targetComponent)) {
                ShizukuLaunchResult.SUCCESS -> finish()

                ShizukuLaunchResult.PERMISSION_REQUIRED -> {
                    launchStarted = false
                    showSetup(permissionInvalidated = true)
                }

                ShizukuLaunchResult.UNAVAILABLE -> {
                    launchStarted = false
                    showSetup(permissionInvalidated = Shizuku.pingBinder())
                }

                ShizukuLaunchResult.TARGET_FAILED -> {
                    launchStarted = false
                    showToast(errorMessageResId)
                    finish()
                }
            }
        }
    }
}