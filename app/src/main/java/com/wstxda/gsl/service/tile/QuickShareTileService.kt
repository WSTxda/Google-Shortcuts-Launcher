package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.QuickShareShortcut
import com.wstxda.gsl.utils.Constants

class QuickShareTileService : ShortcutTileService() {
    override val titleRes = R.string.quick_share
    override val subtitleRes = R.string.share_files
    override val iconRes = R.drawable.ic_shortcut_quick_share
    override val targetActivity = QuickShareShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_QUICK_SHARE_PREF_KEY
}