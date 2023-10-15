package com.wstxda.gsl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class PasswordManagerActivity : Activity() {
    // android.app.Activity
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val url = getString(R.string.password_url)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val message = getString(R.string.browser_not_found)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
