package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Review extends AppCompatActivity {
    DatabaseReference reviewDBRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_page);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String locationName = intent.getStringExtra("name");
        Button addReview = findViewById(R.id.addReviewButton);
        TextView reviewText = findViewById(R.id.reviewText);
        RatingBar rating = findViewById(R.id.ratingBar2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            String firstName = "";
            String lastName = "";
            if(task.isSuccessful() && task.getResult() != null){
                firstName = task.getResult().getString("First Name");
                lastName = task.getResult().getString("Last Name");
                String username = firstName + " " + lastName;
                String loggedInUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reviewDBRef = FirebaseDatabase.getInstance().getReference().child("Review" + locationName);
                addReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int reviewScore = (int) rating.getRating();
                        String scoreToString = Integer.toString(reviewScore);
                        String reviewMessage = reviewText.getText().toString();
                        MyReview review = new MyReview(scoreToString, reviewMessage, username);
                        insertReview(review, loggedInUserID, locationName);
                        Toast.makeText(getApplicationContext(), reviewMessage, Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
    }
    private void insertReview(MyReview review, String loggedUserID, String locationName) {
        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference("Review" + locationName);
        DatabaseReference reviewIdRef = tableRef.child(loggedUserID);
        Map<String, String> reviewData = new HashMap<String, String>();

        reviewData.put("Score", review.getScore());
        reviewData.put("Review", review.getText());
        reviewData.put("Name", review.getName());
        reviewIdRef.setValue(reviewData);

    }

}