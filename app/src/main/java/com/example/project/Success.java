package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Success extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adventures.class);
            startActivity(intent);
        });
    }
}