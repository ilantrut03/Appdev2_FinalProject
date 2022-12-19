package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
    TextView priceView, nameView, countryView, nightsView, peopleView, hostView, descriptionView,  addToFavorite;
    ImageView imageView, back, profile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(DestinationPage.this, R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        String name         = getIntent().getStringExtra("Name");
        String host         = getIntent().getStringExtra("Host");
        String description  = getIntent().getStringExtra("Description");
        String image        = String.valueOf(getIntent().getStringExtra("Image"));
        String nights       = getIntent().getStringExtra("Nights");
        String people       = getIntent().getStringExtra("People");
        String price        = getIntent().getStringExtra("Price");
        String country      = getIntent().getStringExtra("Country");
        String latitude     = getIntent().getStringExtra("Latitude");
        String longitude    = getIntent().getStringExtra("Longitude");

        priceView       = findViewById(R.id.priceView);
        nameView        = findViewById(R.id.destinationName);
        countryView     = findViewById(R.id.destinationCountry);
        peopleView      = findViewById(R.id.destinationPeople);
        hostView        = findViewById(R.id.destinationHost);
        descriptionView = findViewById(R.id.destinationDescription);
        addToFavorite   = findViewById(R.id.destinationAddToFav);

        Button bookButton = findViewById(R.id.showMaps);

        imageView   = (ImageView) findViewById(R.id.imageView);
        back     = findViewById(R.id.imageViewBack);
        profile = findViewById(R.id.profile);

        Glide.with(this).load(image).into(imageView);
        priceView.setText(price);
        nameView.setText(name);
        countryView.setText(country);
        peopleView.setText(people);
        hostView.setText(host);
        descriptionView.setText(description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DestinationPage.this, Adventures.class);
                startActivity(intent);
            }
        });
        /*
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationPage.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        */

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://buy.stripe.com/test_9AQ16K3d69st14Q5kk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Destination destination = new Destination(name, host, description, image, nights, people, price,
                country, latitude, longitude);
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
