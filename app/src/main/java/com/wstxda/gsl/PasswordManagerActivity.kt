package com.wstxda.gsl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class PasswordManagerActivity : Activity() {
    // android.app.Activity
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val intent = Intent("android.intent.action.VIEW")
        intent.data = Uri.parse("https://passwords.google.com")
        startActivity(intent)

    }
}
