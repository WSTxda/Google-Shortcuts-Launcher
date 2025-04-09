package com.wstxda.gsl.shortcuts

import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.utils.Constants

class FilesShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        launchShortcuts(
            listOf(
                Intent(Intent.ACTION_VIEW, Constants.STORAGE_URI),
                Intent("android.provider.action.BROWSE", Constants.STORAGE_URI),
                Intent("android.provider.action.BROWSE_DOCUMENT_ROOT", Constants.STORAGE_URI)
            ), R.string.files_not_found
        )
    }
}