package com.wstxda.gsl.service.tile

import com.wstxda.gsl.R
import com.wstxda.gsl.service.ShortcutTileService
import com.wstxda.gsl.shortcut.GamesShortcut
import com.wstxda.gsl.utils.Constants

class GamesTileService : ShortcutTileService() {
    override val titleRes = R.string.games
    override val subtitleRes = R.string.open_games
    override val iconRes = R.drawable.ic_shortcut_games
    override val targetActivity = GamesShortcut::class.java
    override val tilePreferenceKey = Constants.TILE_GAMES_PREF_KEY
}