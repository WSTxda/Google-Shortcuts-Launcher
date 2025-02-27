package com.wstxda.gsl.shortcuts

import android.content.Intent
import android.net.Uri
import com.wstxda.gsl.R
import com.wstxda.gsl.ui.ShortcutsActivity

class FilesShortcut : ShortcutsActivity() {

    companion object {
        private val STORAGE_URI: Uri =
            Uri.parse("content://com.android.externalstorage.documents/root/primary")
    }

    override fun onCreateInternal() {
        val viewIntent = Intent(Intent.ACTION_VIEW, STORAGE_URI)
        if (tryStartActivity(viewIntent)) return

        val browseIntent = Intent("android.provider.action.BROWSE", STORAGE_URI)
        if (tryStartActivity(browseIntent)) return

        val browseDocIntent = Intent("android.provider.action.BROWSE_DOCUMENT_ROOT", STORAGE_URI)
        if (tryStartActivity(browseDocIntent)) return

        showToast(R.string.files_not_found)
    }
}