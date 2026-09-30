package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.MusicSearchShortcut
import com.wstxda.gsl.utils.Constants

class MusicSearchTileService : ShortcutTileService() {
    override val titleRes = R.string.music_search
    override val subtitleRes = R.string.identify_music
    override val iconRes = R.drawable.ic_shortcut_music_search
    override val targetActivity = MusicSearchShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_MUSIC_SEARCH_PREF_KEY
}