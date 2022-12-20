package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private FirebaseFirestore fStore;
    private String userId;



    Button updateProfileButton;
    Button logout;
    //String DISPLAY_NAME = null;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        updateProfileButton = findViewById(R.id.updateProfile);
        logout = findViewById(R.id.logoutUser);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

            }
        });
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userId);

        final TextView firstnameText = (TextView) findViewById(R.id.firstNameEditText);
        final TextView lastnameText = (TextView) findViewById(R.id.lastNameText);
        final TextView phoneText = (TextView) findViewById(R.id.phoneText);
        final TextView emailText = (TextView) findViewById(R.id.emailText);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phoneText.setText(value.getString("Phone"));
                firstnameText.setText(value.getString("First Name"));
                lastnameText.setText(value.getString("Last Name"));
                emailText.setText(value.getString("Email"));
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditprofileActivity.class);
                i.putExtra("First Name", firstnameText.getText().toString());
                i.putExtra("Last Name", lastnameText.getText().toString());
                i.putExtra("Phone", phoneText.getText().toString() );
                i.putExtra("Email", emailText.getText().toString());
                startActivity(i);


            }
        });
    }

}
