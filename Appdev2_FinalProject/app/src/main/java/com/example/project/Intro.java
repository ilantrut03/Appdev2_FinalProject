package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends AppCompatActivity {
    private static int DISPLAY_TIME = 2100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Intro.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, DISPLAY_TIME);
    }
}