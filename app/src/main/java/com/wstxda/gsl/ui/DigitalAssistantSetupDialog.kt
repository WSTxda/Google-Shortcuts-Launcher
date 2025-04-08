package com.wstxda.gsl.ui

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wstxda.gsl.R
import com.wstxda.gsl.fragments.view.DigitalAssistantPreference
import com.wstxda.gsl.utils.Constants

class DigitalAssistantSetupDialog : DialogFragment() {
    companion object {
        fun show(fragmentManager: FragmentManager, launcher: ActivityResultLauncher<Intent>) {
            if (fragmentManager.findFragmentByTag(Constants.DIGITAL_ASSISTANT_DIALOG_TAG) != null) return
            DigitalAssistantSetupDialog().apply {
                this.launcher = launcher
            }.show(fragmentManager, Constants.DIGITAL_ASSISTANT_DIALOG_TAG)
        }
    }

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val digitalAssistantPreference = DigitalAssistantPreference(
            parentFragment as PreferenceFragmentCompat
        )
        val dialogView = layoutInflater.inflate(R.layout.assistant_setup_dialog, null)
        val dialog = MaterialAlertDialogBuilder(
            requireContext(),
        ).setView(dialogView).setCancelable(false).create()

        dialogView.findViewById<Button>(R.id.positive_button).setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                digitalAssistantPreference.setDigitalAssistSetupStatus(requireContext(), true)
            }
            val intent = Intent(Settings.ACTION_VOICE_INPUT_SETTINGS)
            try {
                launcher.launch(intent)
            } catch (_: Exception) {
            }
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.negative_button).setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }
}