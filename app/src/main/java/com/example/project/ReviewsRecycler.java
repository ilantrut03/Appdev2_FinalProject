package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewsRecycler extends AppCompatActivity implements ListenerInterface {
    RecyclerView recyclerView;
    private DatabaseReference database;
    private ArrayList<MyReview> reviewList;
    private ReviewsRecyclerAdapter reviewsRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.reviewsList);
        Intent intent = getIntent();
        String locationName = intent.getStringExtra("name");
        database = FirebaseDatabase.getInstance().getReference().child("Review" + locationName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        reviewsRecyclerAdapter = new ReviewsRecyclerAdapter(this, this, reviewList);
        recyclerView.setAdapter(reviewsRecyclerAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MyReview review = dataSnapshot.getValue(MyReview.class);
                    reviewList.add(review);

                }
                reviewsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }
}