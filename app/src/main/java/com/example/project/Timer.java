package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Timer extends AppCompatActivity {
    private static int DISPLAY_TIME = 8500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Timer.this, Success.class);
                startActivity(homeIntent);
                finish();
            }
        }, DISPLAY_TIME);
    }


    }
