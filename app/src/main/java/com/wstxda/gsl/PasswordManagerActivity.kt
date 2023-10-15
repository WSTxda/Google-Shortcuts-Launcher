package com.wstxda.gsl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class PasswordManagerActivity : Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val url = getString(R.string.password_url)

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            val errorMessage = getString(R.string.browser_not_found)
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
