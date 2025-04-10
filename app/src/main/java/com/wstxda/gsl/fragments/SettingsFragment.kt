package com.wstxda.gsl.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.SettingsActivity
import com.wstxda.gsl.fragments.preferences.DigitalAssistantPreference
import com.wstxda.gsl.fragments.preferences.ThemePreferences
import com.wstxda.gsl.shortcuts.*
import com.wstxda.gsl.ui.DigitalAssistantSetupDialog
import com.wstxda.gsl.ui.ThemeManager
import com.wstxda.gsl.ui.TileManager
import com.wstxda.gsl.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var themePreferences: ThemePreferences

    private val digitalAssistantPreference by lazy { DigitalAssistantPreference(this) }

    private val digitalAssistantLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewLifecycleOwner.lifecycleScope.launch {
                val isAssistSetupDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
                digitalAssistantPreference.updateDigitalAssistantPreferences(isAssistSetupDone)
                if (!isAssistSetupDone) setupDigitalAssistantPreferences()
            }
        }

    private val shortcuts = mapOf(
        "assistant_shortcut" to AssistantShortcut::class.java,
        "files_shortcut" to FilesShortcut::class.java,
        "games_shortcut" to GamesShortcut::class.java,
        "lens_shortcut" to LensShortcut::class.java,
        "music_search_shortcut" to MusicSearchShortcut::class.java,
        "password_manager_shortcut" to PasswordManagerShortcut::class.java,
        "quick_share_shortcut" to QuickShareShortcut::class.java,
        "weather_shortcut" to WeatherShortcut::class.java,
        "settings_activity" to SettingsActivity::class.java
    )

    private val links = mapOf(
        "developer" to "https://github.com/WSTxda",
        "github_repository" to "https://github.com/WSTxda/Google-Shortcuts-Launcher"
    )

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        themePreferences = ThemePreferences(requireContext())

        setupInitialVisibility()
        setupPreferences()
    }

    override fun onResume() {
        super.onResume()
        setupDigitalAssistantPreferences()
    }

    private fun setupInitialVisibility() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            findPreference<Preference>(Constants.MUSIC_SEARCH_TILE_KEY)?.isVisible = false
        }
    }

    private fun setupPreferences() {
        setupShortcutsActivityPreferences()
        setupTilePreference()
        setupDigitalAssistantPreferences()
        setupThemePreference()
        setupLinkPreferences()
    }

    private fun setupShortcutsActivityPreferences() {
        shortcuts.forEach { (key, activityClass) ->
            (findPreference<SwitchPreferenceCompat>(key))?.setOnPreferenceChangeListener { _, newValue ->
                toggleActivityVisibility(activityClass, newValue as Boolean)
                true
            }
        }
    }

    private fun toggleActivityVisibility(activityClass: Class<*>, showActivity: Boolean) {
        val newState = if (showActivity) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

        requireActivity().packageManager.setComponentEnabledSetting(
            ComponentName(requireContext(), activityClass), newState, PackageManager.DONT_KILL_APP
        )
    }

    private fun setupTilePreference() {
        findPreference<Preference>(Constants.MUSIC_SEARCH_TILE_KEY)?.setOnPreferenceClickListener {
            TileManager(requireContext()).requestAddTile()
            true
        }
    }

    private fun setupDigitalAssistantPreferences() {
        val isAssistSetupDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
        digitalAssistantPreference.updateDigitalAssistantPreferences(isAssistSetupDone)

        if (!isAssistSetupDone) {
            findPreference<Preference>(Constants.DIGITAL_ASSISTANT_SETUP_KEY)?.setOnPreferenceClickListener {
                DigitalAssistantSetupDialog.show(childFragmentManager, digitalAssistantLauncher)
                true
            }
        }
    }

    private fun setupThemePreference() {
        val themePreference = findPreference<ListPreference>("select_theme")
        lifecycleScope.launch {
            val currentTheme = themePreferences.themeFlow.first()
            themePreference?.value = currentTheme
        }

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedTheme = newValue as String
            lifecycleScope.launch {
                themePreferences.saveTheme(selectedTheme)
                ThemeManager.applyTheme(selectedTheme)
            }
            true
        }
    }

    private fun setupLinkPreferences() {
        links.forEach { (key, url) ->
            findPreference<Preference>(key)?.setOnPreferenceClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                true
            }
        }
    }
}