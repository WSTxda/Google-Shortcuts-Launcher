package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.LensShortcut
import com.wstxda.gsl.utils.Constants

class LensTileService : ShortcutTileService() {
    override val titleRes = R.string.lens
    override val subtitleRes = R.string.search
    override val iconRes = R.drawable.ic_shortcut_lens
    override val targetActivity = LensShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_LENS_PREF_KEY
}