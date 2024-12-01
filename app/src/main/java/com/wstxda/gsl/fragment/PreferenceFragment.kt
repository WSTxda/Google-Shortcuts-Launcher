package com.wstxda.gsl.fragment

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.shortcut.*
import com.wstxda.gsl.ui.SettingsActivity
import com.wstxda.gsl.utils.TileManager

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        setupActivityPreferences()
        setupTilePreference()
        setupLinkPreferences()
        updateVersionSummary()
    }

    private fun setupActivityPreferences() {
        val preferences = mapOf(
            "lens_activity" to LensActivity::class.java,
            "assistant_activity" to AssistantActivity::class.java,
            "weather_activity" to WeatherActivity::class.java,
            "password_manager_activity" to PasswordManagerActivity::class.java,
            "game_activity" to GamesActivity::class.java,
            "music_search_activity" to MusicSearchActivity::class.java,
            "settings_activity" to SettingsActivity::class.java,
        )

        preferences.forEach { (key, activityClass) ->
            setupPreference(key, activityClass)
        }
    }

    private fun setupPreference(key: String, activityClass: Class<*>) {
        findPreference<SwitchPreferenceCompat>(key)?.setOnPreferenceChangeListener { _, newValue ->
            val showActivity = newValue as? Boolean == true
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

    private fun setupTilePreference() {
        findPreference<Preference>("add_music_search_tile")?.setOnPreferenceClickListener {
            val tileManager = TileManager(requireContext())
            tileManager.requestAddTile()
            true
        }
    }

    private fun updateVersionSummary() {
        findPreference<Preference>("version")?.summary = getVersionName()
    }

    private fun getVersionName(): String {
        return try {
            val packageInfo: PackageInfo =
                requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            packageInfo.versionName ?: "N/A"
        } catch (_: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }

    private fun setupLinkPreferences() {
        val links = mapOf(
            "developer" to "https://github.com/WSTxda",
            "github_repository" to "https://github.com/WSTxda/Google-Shortcuts-Launcher",
            "version" to "https://github.com/WSTxda/Google-Shortcuts-Launcher/releases/latest"
        )

        links.forEach { (key, url) ->
            setupLinkPreference(key, url)
        }
    }

    private fun setupLinkPreference(key: String, url: String) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            true
        }
    }
}