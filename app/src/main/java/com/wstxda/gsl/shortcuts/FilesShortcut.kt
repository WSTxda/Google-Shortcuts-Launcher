package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity
import com.wstxda.gsl.utils.Constants
import com.wstxda.gsl.utils.ShortcutLauncher

class FilesShortcut : ShortcutsActivity() {

    override fun onCreateInternal() {
        launchShortcuts(
            listOf(
                ShortcutLauncher.LaunchOption(createViewIntent()),
                ShortcutLauncher.LaunchOption(createBrowseIntent()),
                ShortcutLauncher.LaunchOption(createBrowseDocumentRootIntent())
            ), R.string.files_not_found
        )
    }

    private fun createViewIntent() = Intent(Intent.ACTION_VIEW, Constants.STORAGE_URI)
    private fun createBrowseIntent() =
        Intent("android.provider.action.BROWSE", Constants.STORAGE_URI)

    private fun createBrowseDocumentRootIntent() =
        Intent("android.provider.action.BROWSE_DOCUMENT_ROOT", Constants.STORAGE_URI)
}