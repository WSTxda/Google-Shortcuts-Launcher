package com.wstxda.gsl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PasswordManagerActivity extends Activity {
    private Button buttonOpenlink;
    private TextView textOpenlink;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://passwords.google.com"));
        startActivity(intent);
    }
}
