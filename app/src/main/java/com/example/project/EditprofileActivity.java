package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class EditprofileActivity extends AppCompatActivity {

    EditText profileFName, profileLName, profileEmail, profilePhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    Button save;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Intent data = getIntent();
        String fname = data.getStringExtra("First Name");
        String lname = data.getStringExtra("Last Name");
        String phone = data.getStringExtra("Phone");
        String email = data.getStringExtra("Email");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        profileFName = findViewById(R.id.fnameEdit);
        profileLName = findViewById(R.id.lnameEdit);
        profileEmail = findViewById(R.id.emailEdit);
        profilePhone = findViewById(R.id.phoneEdit);
        save = findViewById(R.id.saveBTN);

        profileFName.setText(fname);
        profileLName.setText(lname);
        profilePhone.setText(phone);
        profileEmail.setText(email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileFName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profileLName.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()){
                    Toast.makeText(EditprofileActivity.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }

                String email = profileEmail.getText().toString();
                user.updateEmail(email);
                DocumentReference docRef = fStore.collection("Users").document(user.getUid());
                Map<String,Object> edited = new HashMap<>();
                edited.put("Email", email);
                edited.put("First Name", profileFName.getText().toString());
                edited.put("Last Name", profileLName.getText().toString());
                edited.put("Phone", profilePhone.getText().toString());
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditprofileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                    }
                });

                Toast.makeText(EditprofileActivity.this, "Email is changed.", Toast.LENGTH_SHORT).show();



            }
        });
    }
}