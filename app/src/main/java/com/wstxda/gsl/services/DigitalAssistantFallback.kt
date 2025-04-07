package com.wstxda.gsl.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wstxda.gsl.utils.DigitalAssistantShortcut

class DigitalAssistantFallback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DigitalAssistantShortcut.launchSelectedShortcut(this)
        finish()
    }
}