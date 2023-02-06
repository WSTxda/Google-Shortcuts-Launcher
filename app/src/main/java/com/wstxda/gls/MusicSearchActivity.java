package com.wstxda.gls;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MusicSearchActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Could not start " + intent, e);
            Toast.makeText(getApplicationContext(), "Activity not found. Please install or enable Google App", 1).show();
        }
        finish();
    }
}