package com.wstxda.gsl.services

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

object UpdaterService {
    private const val GITHUB_RELEASE_URL =
        "https://api.github.com/repos/WSTxda/Google-Shortcuts-Launcher/releases/latest"

    fun checkForUpdates(context: Context, anchorView: View) {
        CoroutineScope(Dispatchers.Main).launch {
            if (!isNetworkAvailable(context)) {
                showNoInternetSnackbar(anchorView)
                return@launch
            }

            try {
                val latestVersion = withContext(Dispatchers.IO) { fetchLatestVersion() }
                val currentVersion = getCurrentVersion(context)

                if (compareVersions(currentVersion, latestVersion) < 0) {
                    showUpdateAvailableSnackbar(anchorView, latestVersion)
                } else {
                    showNoUpdateSnackbar(anchorView)
                }
            } catch (e: Exception) {
                showGenericErrorSnackbar(anchorView)
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun fetchLatestVersion(): String {
        val jsonString = URL(GITHUB_RELEASE_URL).readText()
        val jsonObject = JSONObject(jsonString)
        return jsonObject.optString("tag_name").removePrefix("v")
    }

    private fun getCurrentVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "N/A"
        } catch (_: Exception) {
            "N/A"
        }
    }

    private fun compareVersions(current: String, latest: String): Int {
        if (current == "N/A") return -1
        val currentParts = current.split(".").map { it.toIntOrNull() ?: 0 }
        val latestParts = latest.split(".").map { it.toIntOrNull() ?: 0 }

        for (i in 0 until maxOf(currentParts.size, latestParts.size)) {
            val curr = currentParts.getOrNull(i) ?: 0
            val late = latestParts.getOrNull(i) ?: 0
            if (curr != late) return curr - late
        }
        return 0
    }

    private fun showNoUpdateSnackbar(anchorView: View) {
        Snackbar.make(
            anchorView, R.string.update_checker_no_update, Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showUpdateAvailableSnackbar(anchorView: View, latestVersion: String) {
        val snackbar = Snackbar.make(
            anchorView,
            anchorView.context.getString(R.string.update_checker_update_available, latestVersion),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.update_checker_download_button) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(GITHUB_RELEASE_URL.replace("api.", "").replace("/repos", ""))
            )
            anchorView.context.startActivity(intent)
        }
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onShown(snackBar: Snackbar) {
                val params = snackBar.view.layoutParams
                if (params is CoordinatorLayout.LayoutParams) {
                    params.behavior = null
                }
            }
        })
        snackbar.show()
    }

    private fun showNoInternetSnackbar(anchorView: View) {
        Snackbar.make(
            anchorView, R.string.update_checker_no_internet, Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showGenericErrorSnackbar(anchorView: View) {
        Snackbar.make(
            anchorView, R.string.update_checker_generic_error, Snackbar.LENGTH_LONG
        ).show()
    }
}