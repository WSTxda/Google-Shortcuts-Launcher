package com.wstxda.gls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.Toast;

@SuppressLint({"Registered"})
public class PasswordManagerActivity extends Activity {
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.PasswordManagerActivity"));
            startActivity(intent);
            finish();
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Activity not found. Please install or enable Google App", 1).show();
        }
    }
}
