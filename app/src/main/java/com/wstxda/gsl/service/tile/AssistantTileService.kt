package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.AssistantShortcut
import com.wstxda.gsl.utils.Constants

class AssistantTileService : ShortcutTileService() {
    override val titleRes = R.string.assistant
    override val subtitleRes = R.string.start_assistant
    override val iconRes = R.drawable.ic_shortcut_assistant
    override val targetActivity = AssistantShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_ASSISTANT_PREF_KEY
}