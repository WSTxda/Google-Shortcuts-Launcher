package com.wstxda.gsl.services

import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.utils.DigitalAssistantShortcuts

class AssistantFallbackService : ShortcutsActivity() {
    override fun onCreateInternal() {
        DigitalAssistantShortcuts.launchSelectedShortcut(this)
        finish()
    }
}