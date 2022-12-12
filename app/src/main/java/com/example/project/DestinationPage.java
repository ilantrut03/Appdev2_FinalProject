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
    TextView priceView, nameView, countryView, nightsView, peopleView, hostView, descriptionView,  addToFavorite;
    ImageView imageView, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(DestinationPage.this, R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        String name         = getIntent().getStringExtra("Name");
        String host         = getIntent().getStringExtra("Host");
        String description  = getIntent().getStringExtra("Description");
        String coordinates  = getIntent().getStringExtra("Coordinates");
        String image        = String.valueOf(getIntent().getStringExtra("Image"));
        String nights       = getIntent().getStringExtra("Nights");
        String people       = getIntent().getStringExtra("People");
        String price        = getIntent().getStringExtra("Price");

        priceView       = findViewById(R.id.priceView);
        nameView        = findViewById(R.id.destinationName);
        countryView     = findViewById(R.id.destinationCountry);
        nightsView      = findViewById(R.id.destinationNights);
        peopleView      = findViewById(R.id.destinationPeople);
        hostView        = findViewById(R.id.destinationHost);
        descriptionView = findViewById(R.id.destinationDescription);
        addToFavorite   = findViewById(R.id.destinationAddToFav);

        Button bookButton = findViewById(R.id.button);

        imageView   = (ImageView) findViewById(R.id.imageView);
        back     = findViewById(R.id.imageViewBack);

        Glide.with(this).load(image).into(imageView);
        priceView.setText(price);
        nameView.setText(name);
        countryView.setText("Country");
        nightsView.setText(nights);
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

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        Destination destination = new Destination(name, host, description, coordinates, image, nights, people, price);
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
