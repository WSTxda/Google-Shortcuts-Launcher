package com.wstxda.gsl

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        setupPreference("assistant_activity", AssistantActivity::class.java)
        setupPreference("weather_activity", WeatherActivity::class.java)
        setupPreference("password_manager_activity", PasswordManagerActivity::class.java)
        setupPreference("game_activity", GameActivity::class.java)
        setupPreference("music_search_activity", MusicSearchActivity::class.java)
        setupPreference("settings_activity", SettingsActivity::class.java)
        setupLinkPreference("developer", "https://github.com/WSTxda")
        setupLinkPreference(
            "github_repository", "https://github.com/WSTxda/Google-Shortcuts-Launcher"
        )
        findPreference<Preference>("version")?.summary = getVersionName()
    }

    private fun setupPreference(key: String, activityClass: Class<*>) {
        val preference = findPreference<SwitchPreferenceCompat>(key)
        preference?.setOnPreferenceChangeListener { _, newValue ->
            val showActivity = newValue as? Boolean ?: false
            toggleActivityVisibility(activityClass, showActivity)
            true
        }
    }

    private fun toggleActivityVisibility(activityClass: Class<*>, showActivity: Boolean) {
        val packageManager = requireActivity().packageManager
        val componentName = ComponentName(requireContext(), activityClass)

        val newState = if (showActivity) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

        packageManager.setComponentEnabledSetting(
            componentName, newState, PackageManager.DONT_KILL_APP
        )
    }

    private fun getVersionName(): String {
        return try {
            val packageInfo: PackageInfo =
                requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }

    private fun setupLinkPreference(key: String, url: String) {
        val preference = findPreference<Preference>(key)
        preference?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            true
        }
    }
}
