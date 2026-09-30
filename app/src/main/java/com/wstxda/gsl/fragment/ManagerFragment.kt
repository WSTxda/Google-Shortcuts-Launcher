package com.wstxda.gsl.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.quicksettings.TileService
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.LibraryActivity
import com.wstxda.gsl.activity.ManagerActivity
import com.wstxda.gsl.logic.PreferenceHelper
import com.wstxda.gsl.preference.DigitalAssistantPreference
import com.wstxda.gsl.preference.UpdaterPreference
import com.wstxda.gsl.service.tile.*
import com.wstxda.gsl.shortcut.*
import com.wstxda.gsl.shortcut.games.*
import com.wstxda.gsl.ui.TileManager
import com.wstxda.gsl.ui.component.DigitalAssistantSetupDialog
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.viewmodel.ManagerViewModel
import kotlinx.coroutines.launch

class ManagerFragment : PreferenceFragmentCompat() {

    private val viewModel: ManagerViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    private val digitalAssistantPreference by lazy { DigitalAssistantPreference(this) }
    private val preferenceHelper by lazy { PreferenceHelper(requireContext().applicationContext) }
    private val tileManager by lazy { TileManager(requireContext()) }
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
        // apps
        "assistant_shortcut" to AssistantShortcut::class.java,
        "collections_shortcut" to CollectionsShortcut::class.java,
        "files_shortcut" to FilesShortcut::class.java,
        "finance_shortcut" to FinanceShortcut::class.java,
        "games_shortcut" to GamesShortcut::class.java,
        "incognito_shortcut" to IncognitoShortcut::class.java,
        "lens_shortcut" to LensShortcut::class.java,
        "music_search_shortcut" to MusicSearchShortcut::class.java,
        "password_manager_shortcut" to PasswordManagerShortcut::class.java,
        "quick_share_shortcut" to QuickShareShortcut::class.java,
        "scanner_shortcut" to ScannerShortcut::class.java,
        "search_shortcut" to SearchShortcut::class.java,
        "shopping_shortcut" to ShoppingShortcut::class.java,
        "travel_shortcut" to TravelShortcut::class.java,
        "weather_shortcut" to WeatherShortcut::class.java,
        "manager_activity" to ManagerActivity::class.java,
        // games
        "cricket_shortcut" to CricketShortcut::class.java,
        "minesweeper_shortcut" to MinesweeperShortcut::class.java,
        "pacman_shortcut" to PacmanShortcut::class.java,
        "snake_shortcut" to SnakeShortcut::class.java,
        "solitaire_shortcut" to SolitaireShortcut::class.java
    )

    private val tiles: Map<String, Class<out TileService>> = mapOf(
        Constants.TILE_ASSISTANT_PREF_KEY to AssistantTileService::class.java,
        Constants.TILE_GAMES_PREF_KEY to GamesTileService::class.java,
        Constants.TILE_LENS_PREF_KEY to LensTileService::class.java,
        Constants.TILE_MUSIC_SEARCH_PREF_KEY to MusicSearchTileService::class.java,
        Constants.TILE_QUICK_SHARE_PREF_KEY to QuickShareTileService::class.java,
        Constants.TILE_SCANNER_PREF_KEY to ScannerTileService::class.java,
        Constants.TILE_SEARCH_PREF_KEY to SearchTileService::class.java
    )

    private val links = mapOf(
        "developer" to "https://github.com/WSTxda",
        "github_repository" to "https://github.com/WSTxda/Google-Shortcuts-Launcher",
        "license" to "https://github.com/WSTxda/Google-Shortcuts-Launcher/blob/main/LICENSE"
    )

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        observeViewModel()
        setupInitialVisibility()
        setupPreferences()
    }

    private fun observeViewModel() {
        viewModel.isAssistSetupDone.observe(this) { isDone ->
            findPreference<Preference>(Constants.DIGITAL_ASSISTANT_SETUP_PREF_KEY)?.isVisible =
                !isDone
        }
    }

    override fun onResume() {
        super.onResume()
        refreshTilePreferences()
        viewLifecycleOwner.lifecycleScope.launch {
            val isDone = digitalAssistantPreference.checkDigitalAssistSetupStatus()
            viewModel.setAssistSetupDone(isDone)
            digitalAssistantPreference.updateDigitalAssistantPreferences(isDone)
        }
    }

    private fun setupInitialVisibility() {
        findPreference<Preference>(Constants.SHORTCUT_TILES_PREF_KEY)?.isVisible =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    private fun setupPreferences() {
        setupShortcutsActivityPreferences()
        setupTilePreferences()
        setupDigitalAssistantClickListener()
        setupThemePreference()
        setupLibraryPreference()
        setupUpdaterPreference()
        setupLinkPreferences()
    }

    private fun setupTilePreferences() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        tiles.forEach { (key, serviceClass) ->
            findPreference<CheckBoxPreference>(key)?.setOnPreferenceChangeListener { preference, newValue ->
                if (!(newValue as Boolean)) {
                    Snackbar.make(
                        requireView(), R.string.tile_remove_from_panel, Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnPreferenceChangeListener false
                }

                preference.isEnabled = false
                tileManager.requestAddTile(serviceClass) { isTileAdded ->
                    preferenceHelper.setBoolean(key, isTileAdded)
                    if (!isAdded) return@requestAddTile
                    updateTilePreference(key, isTileAdded)
                }
                false
            }
        }
    }

    private fun refreshTilePreferences() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        tiles.keys.forEach { key ->
            updateTilePreference(key, preferenceHelper.getBoolean(key))
        }
    }

    private fun updateTilePreference(key: String, isTileAdded: Boolean) {
        findPreference<CheckBoxPreference>(key)?.apply {
            isEnabled = true
            isChecked = isTileAdded
        }
    }

    private fun setupShortcutsActivityPreferences() {
        shortcuts.forEach { (key, activityClass) ->
            findPreference<SwitchPreferenceCompat>(key)?.setOnPreferenceChangeListener { _, newValue ->
                viewModel.toggleActivityVisibility(activityClass, newValue as Boolean)
                true
            }
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

    private fun setupUpdaterPreference() {
        findPreference<UpdaterPreference>(Constants.UPDATER_PREF_KEY)?.fragmentManager =
            childFragmentManager
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