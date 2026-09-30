package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.SearchShortcut
import com.wstxda.gsl.utils.Constants

class SearchTileService : ShortcutTileService() {
    override val titleRes = R.string.search
    override val subtitleRes = R.string.google_search
    override val iconRes = R.drawable.ic_shortcut_search
    override val targetActivity = SearchShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_SEARCH_PREF_KEY
}