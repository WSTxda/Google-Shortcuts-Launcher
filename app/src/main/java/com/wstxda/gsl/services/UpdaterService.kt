package com.wstxda.gsl.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.wstxda.gsl.R
import com.wstxda.gsl.repository.GitHubReleaseRepository
import com.wstxda.gsl.ui.component.UpdaterBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UpdaterService {

    fun checkForUpdates(
        scope: CoroutineScope,
        context: Context,
        fragmentManager: FragmentManager,
        anchorView: View? = null
    ) {
        scope.launch(Dispatchers.Main) {
            if (!isNetworkAvailable(context)) {
                anchorView?.let {
                    showSnackbar(it, context.getString(R.string.updater_no_internet_message))
                }
                return@launch
            }

            runCatching {
                val release = GitHubReleaseRepository.fetchLatestRelease()
                val current = getInstalledVersion(context)

                if (compareVersions(current, release.version) < 0) {
                    UpdaterBottomSheet.show(fragmentManager, release)
                } else {
                    anchorView?.let {
                        showSnackbar(it, context.getString(R.string.updater_no_update_message))
                    }
                }
            }.onFailure {
                anchorView?.let { view ->
                    showSnackbar(view, context.getString(R.string.updater_generic_error_message))
                }
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService<ConnectivityManager>() ?: return false
        val caps = cm.getNetworkCapabilities(cm.activeNetwork ?: return false) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun getInstalledVersion(context: Context): String = runCatching {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "N/A"
    }.getOrDefault("N/A")

    private fun compareVersions(current: String, latest: String): Int {
        if (current == "N/A") return -1
        val c = current.split(".").map { it.toIntOrNull() ?: 0 }
        val l = latest.split(".").map { it.toIntOrNull() ?: 0 }
        for (i in 0 until maxOf(c.size, l.size)) {
            val diff = (c.getOrElse(i) { 0 }) - (l.getOrElse(i) { 0 })
            if (diff != 0) return diff
        }
        return 0
    }

    private fun showSnackbar(anchorView: View, message: String) {
        Snackbar.make(anchorView, message, Snackbar.LENGTH_SHORT).show()
    }
}