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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DestinationPage extends AppCompatActivity {

    DatabaseReference favDBRef;
    TextView nameView, priceView, seeReviews, addReview, addToFavorite;
    ImageView imageView, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(DestinationPage.this, R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        String name         = getIntent().getStringExtra("Name");
        String coordinates  = getIntent().getStringExtra("Coordinates");
        String price        = getIntent().getStringExtra("Price");
        String image        = String.valueOf(getIntent().getStringExtra("Image"));
        int nights          = getIntent().getIntExtra("Nights" , 0);
        int people          = getIntent().getIntExtra("People", 0);

        nameView       = findViewById(R.id.destinationName);
        priceView      = findViewById(R.id.priceView);
        seeReviews     = findViewById(R.id.seeReviewsText);
        addReview      = findViewById(R.id.destinationAddReview);
        addToFavorite  = findViewById(R.id.destinationAddToFav);

        Button bookButton = findViewById(R.id.button);

        imageView   = (ImageView) findViewById(R.id.imageView);
        backbtn     = findViewById(R.id.imageViewBack);

        nameView.setText(name);
        priceView.setText(price);
        Glide.with(this).load(image).into(imageView);

        seeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewsRecycler.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(DestinationPage.this, Adventures.class);
                startActivity(intent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        Destination destination = new Destination(name, coordinates, image, price, nights, people);
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
                    favDBRef.child(destination.getName()).setValue(destination);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });
    }
}
