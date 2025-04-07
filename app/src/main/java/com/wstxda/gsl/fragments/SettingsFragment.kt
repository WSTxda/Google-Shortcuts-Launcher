package com.wstxda.gsl.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.fragments.view.DigitalAssistantPreference
import com.wstxda.gsl.shortcuts.*
import com.wstxda.gsl.ui.SettingsActivity
import com.wstxda.gsl.ui.TileManager
import com.wstxda.gsl.ui.ThemeManager
import androidx.core.net.toUri
import com.wstxda.gsl.ui.DigitalAssistantSetupDialog

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val MUSIC_SEARCH_TILE_KEY = "add_music_search_tile"
        private const val DIGITAL_ASSISTANT_SETUP_KEY = "digital_assistant_setup"
        private const val SELECT_THEME_KEY = "select_theme"
    }

    private val digitalAssistantPreference: DigitalAssistantPreference by lazy {
        DigitalAssistantPreference(
            this
        )
    }

    private val digitalAssistantLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val isAssistSetupDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
            digitalAssistantPreference.updateDigitalAssistantPreferences(isAssistSetupDone)
            if (!isAssistSetupDone) {
                setupDigitalAssistantPreferences()
            }
        }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            findPreference<Preference>(MUSIC_SEARCH_TILE_KEY)?.isVisible = false
            findPreference<Preference>(DIGITAL_ASSISTANT_SETUP_KEY)?.isVisible = false
        }

        setupShortcutsActivityPreferences()
        setupTilePreference()
        setupDigitalAssistantPreferences()
        setupThemePreference()
        setupLinkPreferences()
    }

    override fun onResume() {
        super.onResume()
        setupDigitalAssistantPreferences()
    }

    private fun setupPreference(key: String, activityClass: Class<*>) {
        findPreference<SwitchPreferenceCompat>(key)?.setOnPreferenceChangeListener { _, newValue ->
            toggleActivityVisibility(activityClass, newValue as? Boolean == true)
            true
        }
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

    private fun toggleActivityVisibility(activityClass: Class<*>, showActivity: Boolean) {
        val packageManager = requireActivity().packageManager
        val componentName = ComponentName(requireContext(), activityClass)
        val newState = if (showActivity) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }
        packageManager.setComponentEnabledSetting(
            componentName, newState, PackageManager.DONT_KILL_APP
        )
    }

    private fun setupTilePreference() {
        findPreference<Preference>(MUSIC_SEARCH_TILE_KEY)?.setOnPreferenceClickListener {
            val tileManager = TileManager(requireContext())
            tileManager.requestAddTile()
            true
        }
    }

    private fun setupDigitalAssistantPreferences() {
        val isAssistSetupDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
        digitalAssistantPreference.updateDigitalAssistantPreferences(isAssistSetupDone)

        if (!isAssistSetupDone) {
            findPreference<Preference>(DIGITAL_ASSISTANT_SETUP_KEY)?.setOnPreferenceClickListener {
                DigitalAssistantSetupDialog.show(childFragmentManager, digitalAssistantLauncher)
                true
            }
        }
    }

    private fun setupThemePreference() {
        findPreference<ListPreference>(SELECT_THEME_KEY)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                ThemeManager.setupTheme(newValue as String)
                true
            }
            ThemeManager.setupTheme(value)
        }
    }

    private fun setupLinkPreferences() {
        val links = mapOf(
            "developer" to "https://github.com/WSTxda",
            "github_repository" to "https://github.com/WSTxda/Google-Shortcuts-Launcher",
        )

        links.forEach { (key, url) ->
            setupLinkPreference(key, url)
        }
    }

    private fun setupLinkPreference(key: String, url: String) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
            true
        }
    }
}