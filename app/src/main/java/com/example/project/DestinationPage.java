package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.URI;

public class DestinationPage extends AppCompatActivity {

    DatabaseReference favDBRef;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(DestinationPage.this, R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        String name = getIntent().getStringExtra("Name");
        String price = getIntent().getStringExtra("Price");
        String image = String.valueOf(getIntent().getStringExtra("Image"));


        TextView nameView = findViewById(R.id.destinationName);
        TextView priceView = findViewById(R.id.priceView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        backbtn = findViewById(R.id.imageViewBack);
        nameView.setText(name);
        priceView.setText(price);
        Glide.with(this).load(image).into(imageView);
        TextView seeReviews = findViewById(R.id.seeReviewsText);
        seeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewsRecycler.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        TextView addReview = findViewById(R.id.destinationAddReview);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Review.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DestinationPage.this, Recycler.class);
                startActivity(intent);
            }
        });

        Button bookButton = findViewById(R.id.button);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        TextView addToFavorite = findViewById(R.id.destinationAddToFav);
        Destination destination = new Destination(name, image, price);
        String loggedInUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favDBRef = FirebaseDatabase.getInstance().getReference().child("Favorites" + loggedInUserID);
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDestinationIntoFavorites(destination, loggedInUserID);
            }
        });


    }
    private void insertDestinationIntoFavorites(Destination destination, String loggedUserID) {
        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference("Favorites" + loggedUserID);
        DatabaseReference giftIdRef = tableRef.child("location_name");
        giftIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    favDBRef.child(destination.getLocation_name()).setValue(destination);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
