package com.wstxda.gls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.Toast;

@SuppressLint({"Registered"})
public class WeatherActivity extends Activity {
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setComponent(new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.apps.search.weather.WeatherExportedActivity"));
            startActivity(intent);
            finish();
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), R.string.activity_not_found, 1).show();
        }
    }
}