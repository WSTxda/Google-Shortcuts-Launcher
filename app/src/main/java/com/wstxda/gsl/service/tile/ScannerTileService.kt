package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.ScannerShortcut
import com.wstxda.gsl.utils.Constants

class ScannerTileService : ShortcutTileService() {
    override val titleRes = R.string.scanner
    override val subtitleRes = R.string.scan_code
    override val iconRes = R.drawable.ic_shortcut_scanner
    override val targetActivity = ScannerShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_SCANNER_PREF_KEY
}