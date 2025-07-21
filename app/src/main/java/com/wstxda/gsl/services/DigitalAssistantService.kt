package com.wstxda.gsl.services

import com.wstxda.gsl.activity.ShortcutsActivity
import com.wstxda.gsl.utils.DigitalAssistantShortcuts

class DigitalAssistantService : ShortcutsActivity() {
    override fun onCreateInternal() {
        DigitalAssistantShortcuts.launchSelectedShortcut(this)
    }
}