package com.wstxda.gsl.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.shortcuts.*
import com.wstxda.gsl.ui.SettingsActivity
import com.wstxda.gsl.ui.TileManager
import androidx.core.net.toUri

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            findPreference<Preference>("add_music_search_tile")?.isVisible = false
        }

        setupShortcutsActivityPreferences()
        setupTilePreference()
        setupLinkPreferences()
    }

    private fun setupShortcutsActivityPreferences() {
        val preferences = mapOf(
            "assistant_shortcut" to AssistantShortcut::class.java,
            "files_shortcut" to FilesShortcut::class.java,
            "games_shortcut" to GamesShortcut::class.java,
            "lens_shortcut" to LensShortcut::class.java,
            "music_search_shortcut" to MusicSearchShortcut::class.java,
            "password_manager_shortcut" to PasswordManagerShortcut::class.java,
            "quick_share_shortcut" to QuickShareShortcut::class.java,
            "weather_shortcut" to WeatherShortcut::class.java,
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

    private fun setupLinkPreferences() {
        val links = mapOf(
            "developer" to "https://github.com/WSTxda",
            "github_repository" to "https://github.com/WSTxda/Google-Shortcuts-Launcher",
            // "version" to "https://github.com/WSTxda/Google-Shortcuts-Launcher/releases/latest"
        )

        links.forEach { (key, url) ->
            setupLinkPreference(key, url)
        }
    }

    private fun setupLinkPreference(key: String, url: String) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
            true
        }
    }
}