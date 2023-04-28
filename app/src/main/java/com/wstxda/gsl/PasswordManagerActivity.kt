package com.wstxda.gsl

import android.app.Activity
import android.content.Intent
import android.widget.Toast


class PasswordManagerActivity : Activity() {
    public override fun onResume() {
        super.onResume()
        try {
            startActivity(Intent("android.settings.SYNC_SETTINGS"))
            finish()
        } catch (unused: Exception) {
            Toast.makeText(
                applicationContext,
                getString(R.string.activity_not_supported),
                1
            ).show()
        }
    }
}