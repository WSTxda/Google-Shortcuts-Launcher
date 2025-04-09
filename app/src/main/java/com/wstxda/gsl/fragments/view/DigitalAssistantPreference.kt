package com.wstxda.gsl.fragments.view

import android.content.Context
import android.os.Build
import androidx.core.content.edit
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class DigitalAssistantPreference(private val fragment: PreferenceFragmentCompat) {
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(fragment.requireContext()) }

    fun checkDigitalAssistSetupStatus(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            fragment.requireContext().getSystemService(android.app.role.RoleManager::class.java)
                .isRoleHeld(android.app.role.RoleManager.ROLE_ASSISTANT)
        } else {
            prefs.getBoolean("is_assist_setup_done", false)
        }

    fun setDigitalAssistSetupStatus(context: Context, isSetupDone: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putBoolean("is_assist_setup_done", isSetupDone)
        }
    }

    fun updateDigitalAssistantPreferences(isAssistSetupDone: Boolean) {
        fragment.findPreference<androidx.preference.Preference>("digital_assistant_setup")?.isVisible =
            !isAssistSetupDone
        fragment.findPreference<androidx.preference.ListPreference>("digital_assistant_shortcut")
            ?.apply {
                isVisible = isAssistSetupDone
                isEnabled = isAssistSetupDone
            }
    }
}