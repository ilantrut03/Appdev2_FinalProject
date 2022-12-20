package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity implements ListenerInterface {

    RecyclerView recyclerView;
    private DatabaseReference database;
    private ArrayList<Destination> favoriteList;
    private FavoritesAdapter favoriteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.favDestinations);
        String loggedInUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference("Favorites" + loggedInUserID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteList = new ArrayList<>();
        favoriteRecyclerAdapter = new FavoritesAdapter(this, favoriteList, this);
        recyclerView.setAdapter(favoriteRecyclerAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Destination destination = dataSnapshot.getValue(Destination.class);
                    favoriteList.add(destination);
                }
                favoriteRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        EditText searchBar = findViewById(R.id.favSearchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterDestinationName((editable.toString()));
            }
        });
    }

    private void filterDestinationName(String searchTitle) {
        ArrayList<Destination> favDestFilterList = new ArrayList<>();
        for (Destination destination : favoriteList) {
            if (destination.getName().toLowerCase().contains(searchTitle.toLowerCase())) {
                favDestFilterList.add(destination);
            }
        }
        favoriteRecyclerAdapter.filterGiftNameList(favDestFilterList);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Favorites.this, DestinationPage.class);
        intent.putExtra("Name", favoriteList.get(position).getName());
        intent.putExtra("Image", favoriteList.get(position).getImageUrl());
        intent.putExtra("Price", favoriteList.get(position).getPrice());
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}