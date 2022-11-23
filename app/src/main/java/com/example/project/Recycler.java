package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Recycler extends AppCompatActivity implements ListenerInterface  { // new
    RecyclerView recyclerView;
    private DatabaseReference database;
    private ArrayList<Destination> destinationsList;
    private RecyclerAdapter recyclerAdapter;
    private ImageView backbtn;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Recycler.this, R.color.black));
        recyclerView = findViewById(R.id.destinations);
        backbtn = findViewById(R.id.imageButtonRecycle);
        database = FirebaseDatabase.getInstance().getReference("Locations");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        destinationsList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, destinationsList, this);
        recyclerView.setAdapter(recyclerAdapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recycler.this, MainActivity.class);
                startActivity(intent);
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Destination destination = dataSnapshot.getValue(Destination.class);
                    destinationsList.add(destination);

                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override

            public void afterTextChanged(Editable editable) {
                filterGiftName(editable.toString());
            }
        });
    }
    private void filterGiftName(String searchTitle) {
        ArrayList<Destination> destinationsNamesList = new ArrayList<>();
        for (Destination destination : destinationsList) {
            if (destination.getLocation_name().toLowerCase().contains(searchTitle.toLowerCase())) {
                destinationsNamesList.add(destination);
            }
        }
        recyclerAdapter.filterGiftNameList(destinationsNamesList);
    }


    @Override
    public void onItemClick(int position) {     // new

        Intent intent = new Intent(Recycler.this, DestinationPage.class);
        intent.putExtra("Image", destinationsList.get(position).getImageUrl());
        intent.putExtra("Name", destinationsList.get(position).getLocation_name());
        intent.putExtra("Price", destinationsList.get(position).getPrice());
        startActivity(intent);
        finish();
    }
}
