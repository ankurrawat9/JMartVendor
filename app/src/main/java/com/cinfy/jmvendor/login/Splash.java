package com.cinfy.jmvendor.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cinfy.jmvendor.dashboard.DashBoardActivity;
import com.cinfy.jmvendor.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Splash.this == null) {
                    return;
                }
                Intent myProfileIntent = new Intent(Splash.this, DashBoardActivity.class);
                startActivity(myProfileIntent);
                finish();

            }
        }, 2000);
    }
}