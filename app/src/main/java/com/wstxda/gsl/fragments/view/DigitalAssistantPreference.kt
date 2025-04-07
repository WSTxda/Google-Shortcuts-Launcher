package com.wstxda.gsl.fragments.view

import android.app.role.RoleManager
import android.content.Context
import android.os.Build
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.core.content.edit

class DigitalAssistantPreference(private val fragment: PreferenceFragmentCompat) {

    fun checkDigitalAssistSetupStatus(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val roleManager = fragment.requireContext().getSystemService(RoleManager::class.java)
            roleManager.isRoleHeld(RoleManager.ROLE_ASSISTANT)
        } else {
            PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())
                .getBoolean("is_assist_setup_done", false)
        }
    }

    fun setDigitalAssistSetupStatus(context: Context, isSetupDone: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit() {
            putBoolean("is_assist_setup_done", isSetupDone)
        }
    }

    fun updateDigitalAssistantPreferences(isAssistSetupDone: Boolean) {
        fragment.findPreference<Preference>("digital_assistant_setup")?.isVisible = !isAssistSetupDone
        val assistShortcutPref = fragment.findPreference<ListPreference>("digital_assistant_shortcut")
        assistShortcutPref?.isVisible = isAssistSetupDone
        assistShortcutPref?.isEnabled = isAssistSetupDone
    }
}