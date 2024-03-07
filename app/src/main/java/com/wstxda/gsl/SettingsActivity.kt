package com.wstxda.gsl

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_container, SettingsFragment()).commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            setupPreference("weather_activity", WeatherActivity::class.java)
            setupPreference("password_manager_activity", PasswordManagerActivity::class.java)
            setupPreference("game_activity", GameActivity::class.java)
            setupPreference("music_search_activity", MusicSearchActivity::class.java)
            setupPreference("settings_activity", SettingsActivity::class.java)
            developerLink("developer")
            githubRepository("github_repository")
            findPreference<Preference>("version")?.summary = getVersionName()

        }

        private fun setupPreference(key: String, activityClass: Class<*>) {
            val preference = findPreference<SwitchPreferenceCompat>(key)
            preference?.setOnPreferenceChangeListener { _, newValue ->
                val hideActivity = newValue as? Boolean ?: false
                toggleActivityVisibility(activityClass, hideActivity)
                true
            }
        }

        private fun toggleActivityVisibility(activityClass: Class<*>, hideActivity: Boolean) {
            val packageManager = requireActivity().packageManager
            val componentName = ComponentName(requireContext(), activityClass)

            val newState = if (hideActivity) PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            else PackageManager.COMPONENT_ENABLED_STATE_ENABLED

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

        private fun developerLink(key: String) {
            val preference = findPreference<Preference>(key)
            preference?.setOnPreferenceClickListener {
                val url = "https://github.com/WSTxda"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                true
            }
        }

        private fun githubRepository(key: String) {
            val preference = findPreference<Preference>(key)
            preference?.setOnPreferenceClickListener {
                val url = "https://github.com/WSTxda/Google-Shortcuts-Launcher"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                true
            }
        }
    }
}