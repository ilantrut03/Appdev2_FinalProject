package com.example.project;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class DestinationPage extends AppCompatActivity {

    DatabaseReference favDBRef;
    TextView priceView, nameView, countryView, nightsView, peopleView, hostView, descriptionView,  addToFavorite, destinationAddToFav;
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
        String country        = getIntent().getStringExtra("Country");
        String latitude        = getIntent().getStringExtra("Latitude");
        String longitude        = getIntent().getStringExtra("Longitude");

        priceView       = findViewById(R.id.priceView);
        nameView        = findViewById(R.id.destinationName);
        countryView     = findViewById(R.id.destinationCountry);
        nightsView      = findViewById(R.id.destinationNights);
        peopleView      = findViewById(R.id.destinationPeople);
        hostView        = findViewById(R.id.destinationHost);
        descriptionView = findViewById(R.id.destinationDescription);
        addToFavorite   = findViewById(R.id.destinationAddToFav);
        destinationAddToFav = findViewById(R.id.destinationAddToFav);

        Button bookButton = findViewById(R.id.showMaps);
        Destination destination = new Destination(name, host, description, coordinates, image, nights,
                people, price, country, latitude, longitude);

        imageView   = (ImageView) findViewById(R.id.imageView);
        back     = findViewById(R.id.imageViewBack);

        Glide.with(this).load(image).into(imageView);
        priceView.setText(price);
        nameView.setText(name);
        countryView.setText(country);
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

        destinationAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast message = Toast.makeText(getApplicationContext() , "Destination added to favourites!", Toast.LENGTH_LONG);
                message.show();
                insertDataIntoFavourites(destination);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://buy.stripe.com/test_9AQ16K3d69st14Q5kk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        String loggedInUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favDBRef = FirebaseDatabase.getInstance().getReference().child("Favorites" + loggedInUserID);
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDestinationIntoFavorites(destination, loggedInUserID);
            }
        });
    }


    public void insertDataIntoFavourites(Destination destination){
        String loggedInUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String latitude = destination.getLatitude();
        String longitude = destination.getLongitude();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("favourites").child(destination.getName()).setValue(loggedInUserID);
        mDatabase.child("favourites").child(destination.getLatitude()).setValue(latitude);
        mDatabase.child("favourites").child(destination.getLongitude()).setValue(longitude);
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
