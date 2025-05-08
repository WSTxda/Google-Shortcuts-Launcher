package com.wstxda.gsl.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.LibraryActivity
import com.wstxda.gsl.activity.SettingsActivity
import com.wstxda.gsl.fragments.preferences.DigitalAssistantPreference
import com.wstxda.gsl.shortcuts.*
import com.wstxda.gsl.ui.DigitalAssistantSetupDialog
import com.wstxda.gsl.ui.TileManager
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    private val digitalAssistantPreference by lazy { DigitalAssistantPreference(this) }
    private val digitalAssistantLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            val isDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
            viewModel.setAssistSetupDone(isDone)
            digitalAssistantPreference.updateDigitalAssistantPreferences(isDone)
            if (!isDone) setupDigitalAssistantClickListener()
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
        observeViewModel()
        setupInitialVisibility()
        setupLibraryPreference()
        setupPreferences()
    }

    private fun observeViewModel() {
        viewModel.isAssistSetupDone.observe(this) { isDone ->
            findPreference<Preference>(Constants.DIGITAL_ASSISTANT_SETUP_PREF_KEY)?.isVisible = !isDone
        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            val isDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
            viewModel.setAssistSetupDone(isDone)
            digitalAssistantPreference.updateDigitalAssistantPreferences(isDone)
        }
    }

    private fun setupInitialVisibility() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            findPreference<Preference>(Constants.MUSIC_SEARCH_TILE_KEY)?.isVisible = false
        }
    }

    private fun setupPreferences() {
        setupShortcutsActivityPreferences()
        setupTilePreference()
        setupDigitalAssistantClickListener()
        setupThemePreference()
        setupLibraryPreference()
        setupLinkPreferences()
    }

    private fun setupShortcutsActivityPreferences() {
        shortcuts.forEach { (key, activityClass) ->
            findPreference<SwitchPreferenceCompat>(key)?.setOnPreferenceChangeListener { _, newValue ->
                viewModel.toggleActivityVisibility(activityClass, newValue as Boolean)
                true
            }
        }
    }

    private fun setupTilePreference() {
        findPreference<Preference>(Constants.MUSIC_SEARCH_TILE_KEY)?.setOnPreferenceClickListener {
            TileManager(requireContext()).requestAddTile()
            true
        }
    }

    private fun setupDigitalAssistantClickListener() {
        findPreference<Preference>(Constants.DIGITAL_ASSISTANT_SETUP_PREF_KEY)?.setOnPreferenceClickListener {
            DigitalAssistantSetupDialog.show(childFragmentManager, digitalAssistantLauncher)
            true
        }
    }

    private fun setupThemePreference() {
        findPreference<ListPreference>(Constants.THEME_PREF_KEY)?.setOnPreferenceChangeListener { _, newValue ->
            viewModel.applyTheme(newValue.toString())
            true
        }
    }

    private fun setupLibraryPreference() {
        findPreference<Preference>(Constants.LIBRARY_PREF_KEY)?.setOnPreferenceClickListener {
            val intent = Intent(requireContext(), LibraryActivity::class.java)
            startActivity(intent)
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