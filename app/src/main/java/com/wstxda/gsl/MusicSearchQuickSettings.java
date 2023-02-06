package com.wstxda.gsl;

import android.content.Intent;
import android.service.quicksettings.TileService;

public class MusicSearchQuickSettings extends TileService {
    @Override // android.service.quicksettings.TileService
    public void onClick() {
        super.onClick();
        startActivityAndCollapse(new Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").setFlags(268435456));
    }
}