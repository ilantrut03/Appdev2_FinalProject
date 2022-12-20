package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        email = findViewById(R.id.forgotPasswordEmail);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ImageButton backButton = findViewById(R.id.forgotPasswordBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LoginFragment.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        Button resetPasswordButton = findViewById(R.id.resetPasswordButton);
        auth = FirebaseAuth.getInstance();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        email = findViewById(R.id.forgotPasswordEmail);
        String emailString = email.getText().toString();
        if (emailString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email provided is empty!", Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            Toast.makeText(getApplicationContext(), "Please provide a correct email!", Toast.LENGTH_LONG).show();
        }

        auth.sendPasswordResetEmail(emailString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Please verify your email to reset your password!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, LoginFragment.class);
                    overridePendingTransition(0, 0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}