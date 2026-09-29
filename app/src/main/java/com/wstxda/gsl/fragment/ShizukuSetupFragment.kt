package com.wstxda.gsl.fragment

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.wstxda.gsl.R
import com.wstxda.gsl.data.ShizukuLaunchResult
import com.wstxda.gsl.data.ShizukuState
import com.wstxda.gsl.databinding.FragmentShizukuSetupBinding
import com.wstxda.gsl.logic.ShizukuChecker
import com.wstxda.gsl.logic.ShizukuLauncher
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.launch
import rikka.shizuku.Shizuku

class ShizukuSetupFragment : Fragment() {

    companion object {
        fun newInstance(
            component: ComponentName, errorMessageResId: Int, permissionInvalidated: Boolean = false
        ) = ShizukuSetupFragment().apply {
            arguments = Bundle().apply {
                putString(Constants.EXTRA_SHIZUKU_TARGET_COMPONENT, component.flattenToString())
                putInt(Constants.EXTRA_SHIZUKU_ERROR_MESSAGE, errorMessageResId)
                putBoolean(Constants.STATE_SHIZUKU_PERMISSION_INVALIDATED, permissionInvalidated)
            }
        }
    }

    private var _binding: FragmentShizukuSetupBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var targetComponent: ComponentName
    private var errorMessageResId = R.string.shortcut_invalid
    private var launchStarted = false
    private var permissionInvalidated = false
    private var binderDied = false

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        activity?.runOnUiThread {
            val context = context ?: return@runOnUiThread
            val state = ShizukuChecker.getState(context)
            if (binderDied && state == ShizukuState.READY) permissionInvalidated = false
            binderDied = false
            refreshState(state)
        }
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        activity?.runOnUiThread {
            binderDied = true
            refreshState()
        }
    }

    private val permissionResultListener =
        Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
            if (requestCode == Constants.SHIZUKU_PERMISSION_REQUEST_CODE) {
                activity?.runOnUiThread {
                    permissionInvalidated = grantResult != PackageManager.PERMISSION_GRANTED
                    refreshState(
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            ShizukuState.READY
                        } else {
                            ShizukuState.PERMISSION_REQUIRED
                        }
                    )
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = ComponentName.unflattenFromString(
            requireArguments().getString(Constants.EXTRA_SHIZUKU_TARGET_COMPONENT).orEmpty()
        )
        if (component == null) {
            requireActivity().finish()
            return
        }

        targetComponent = component
        errorMessageResId = requireArguments().getInt(
            Constants.EXTRA_SHIZUKU_ERROR_MESSAGE, R.string.shortcut_invalid
        )
        permissionInvalidated = savedInstanceState?.getBoolean(
            Constants.STATE_SHIZUKU_PERMISSION_INVALIDATED
        ) ?: requireArguments().getBoolean(Constants.STATE_SHIZUKU_PERMISSION_INVALIDATED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShizukuSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            shizukuTitleStart.setText(R.string.shizuku_start_title)
            shizukuMessageStart.setText(R.string.shizuku_start_message)
            shizukuTitlePermission.setText(R.string.shizuku_permission_title)
            shizukuMessagePermission.setText(R.string.shizuku_permission_message)
            shizukuInfoMessage.setText(R.string.shizuku_info_message)
            shizukuButtonContinue.setText(R.string.shizuku_continue)
            shizukuButtonContinue.setOnClickListener { launchTarget() }
        }

        refreshState()
    }

    override fun onStart() {
        super.onStart()
        Shizuku.addBinderReceivedListener(binderReceivedListener)
        Shizuku.addBinderDeadListener(binderDeadListener)
        Shizuku.addRequestPermissionResultListener(permissionResultListener)
    }

    override fun onResume() {
        super.onResume()
        refreshState()
    }

    override fun onStop() {
        Shizuku.removeBinderReceivedListener(binderReceivedListener)
        Shizuku.removeBinderDeadListener(binderDeadListener)
        Shizuku.removeRequestPermissionResultListener(permissionResultListener)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(Constants.STATE_SHIZUKU_PERMISSION_INVALIDATED, permissionInvalidated)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun refreshState(state: ShizukuState = getState()) {
        val binding = _binding ?: return

        binding.shizukuMessageStart.setText(R.string.shizuku_start_message)
        binding.shizukuButtonStart.apply {
            setIconResource(R.drawable.ic_open)
            isEnabled = true
            setOnClickListener(null)
        }
        binding.shizukuButtonPermission.apply {
            setIconResource(R.drawable.ic_open)
            isEnabled = false
            setOnClickListener(null)
        }
        binding.shizukuButtonContinue.apply {
            isEnabled = false
        }

        when (state) {
            ShizukuState.NOT_INSTALLED -> {
                binding.shizukuButtonStart.apply {
                    setIconResource(R.drawable.ic_download)
                    setText(R.string.shizuku_install)
                    setOnClickListener { openDownloadPage() }
                }
                binding.shizukuButtonPermission.setText(R.string.shizuku_grant_access)
            }

            ShizukuState.NOT_RUNNING -> {
                binding.shizukuButtonStart.setText(R.string.shizuku_open)
                binding.shizukuButtonStart.setOnClickListener { openShizuku() }
                binding.shizukuButtonPermission.setText(R.string.shizuku_grant_access)
            }

            ShizukuState.UNSUPPORTED -> {
                binding.shizukuMessageStart.setText(R.string.shizuku_unsupported_message)
                binding.shizukuButtonStart.setText(R.string.shizuku_open)
                binding.shizukuButtonStart.setOnClickListener { openShizuku() }
                binding.shizukuButtonPermission.setText(R.string.shizuku_grant_access)
            }

            ShizukuState.PERMISSION_REQUIRED -> {
                setStartReadyState()
                binding.shizukuButtonPermission.apply {
                    isEnabled = true
                    setText(R.string.shizuku_grant_access)
                    setOnClickListener { requestPermission() }
                }
            }

            ShizukuState.READY -> {
                setStartReadyState()
                binding.shizukuButtonPermission.apply {
                    setIconResource(R.drawable.ic_check_circle)
                    isEnabled = false
                    setText(R.string.shizuku_granted)
                }
                binding.shizukuButtonContinue.isEnabled = !launchStarted
            }
        }
    }

    private fun setStartReadyState() {
        binding.shizukuButtonStart.apply {
            setIconResource(R.drawable.ic_check_circle)
            isEnabled = false
            setText(R.string.shizuku_ready)
        }
    }

    private fun getState(): ShizukuState {
        val state = ShizukuChecker.getState(requireContext())
        return if (permissionInvalidated && state == ShizukuState.READY) {
            ShizukuState.PERMISSION_REQUIRED
        } else {
            state
        }
    }

    private fun requestPermission() {
        if (getState() != ShizukuState.PERMISSION_REQUIRED) {
            refreshState()
            return
        }

        runCatching {
            Shizuku.requestPermission(Constants.SHIZUKU_PERMISSION_REQUEST_CODE)
        }.onFailure {
            refreshState()
        }
    }

    private fun openDownloadPage() {
        requireContext().launchShortcuts(
            listOf(Intent(Intent.ACTION_VIEW, Constants.SHIZUKU_DOWNLOAD_URL.toUri())),
            R.string.browser_not_found
        )
    }

    private fun openShizuku() {
        val context = requireContext()
        val intent = context.packageManager.getLaunchIntentForPackage(Constants.SHIZUKU_PACKAGE)
        if (intent == null || !context.launchShortcuts(intent)) openDownloadPage()
    }

    private fun launchTarget() {
        if (launchStarted || getState() != ShizukuState.READY) {
            refreshState()
            return
        }

        launchStarted = true
        binding.shizukuButtonContinue.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {
            when (ShizukuLauncher.launch(requireContext(), targetComponent)) {
                ShizukuLaunchResult.SUCCESS -> requireActivity().finish()

                ShizukuLaunchResult.PERMISSION_REQUIRED -> {
                    launchStarted = false
                    permissionInvalidated = true
                    refreshState(ShizukuState.PERMISSION_REQUIRED)
                }

                ShizukuLaunchResult.UNAVAILABLE -> {
                    launchStarted = false
                    permissionInvalidated = Shizuku.pingBinder()
                    refreshState()
                }

                ShizukuLaunchResult.TARGET_FAILED -> {
                    launchStarted = false
                    requireContext().showToast(errorMessageResId)
                    requireActivity().finish()
                }
            }
        }
    }
}