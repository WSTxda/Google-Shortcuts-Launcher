package com.wstxda.gsl.shortcuts

import android.content.ComponentName
import android.content.Intent
import com.wstxda.gsl.R
import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.logic.launchShortcuts
import com.wstxda.gsl.logic.showToast

class ScannerShortcut : ShortcutsActivity() {
    override fun onCreateInternal() {
        if (!launchShortcuts(createBarcodeIntent())) {
            showToast(R.string.play_services_not_found)
        }
    }

    private fun createBarcodeIntent() = Intent().apply {
        component = ComponentName(
            "com.google.android.gms", "com.google.android.gms.mlkit.barcode.v2.ScannerActivity"
        )
    }
}