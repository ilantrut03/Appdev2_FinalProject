package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adventures extends AppCompatActivity implements ListenerInterface  {

    private RecyclerView recyclerView;
    private ArrayList<Destination> destinationsList;
    private AdventuresAdapter recyclerAdapter;

    private ImageView backbtn;
    private EditText searchBar;

    private DatabaseReference database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Adventures.this, R.color.black));

        searchBar       = findViewById(R.id.searchBar);
        recyclerView    = findViewById(R.id.destinations);
        backbtn         = findViewById(R.id.imageButtonRecycle);
        database        = FirebaseDatabase.getInstance().getReference("location");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        destinationsList = new ArrayList<>();
        recyclerAdapter = new AdventuresAdapter(this, destinationsList, this);
        recyclerView.setAdapter(recyclerAdapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adventures.this, MainActivity.class);
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
                Log.e("Firebase Database Error", error.toString());
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void afterTextChanged(Editable editable) { filterGiftName(editable.toString()); }
        });
    }

    private void filterGiftName(String searchTitle) {
        ArrayList<Destination> destinationsNamesList = new ArrayList<>();
        for (Destination destination : destinationsList) {
            if (destination.getName().toLowerCase().contains(searchTitle.toLowerCase())) {
                destinationsNamesList.add(destination);
            }
        }
        recyclerAdapter.filterGiftNameList(destinationsNamesList);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Adventures.this, DestinationPage.class);

        intent.putExtra("Name",         destinationsList.get(position).getName());
        intent.putExtra("Host",         destinationsList.get(position).getHost());
        intent.putExtra("Description",  destinationsList.get(position).getDescription());
        intent.putExtra("Image",        destinationsList.get(position).getImageUrl());
        intent.putExtra("Price",        destinationsList.get(position).getPrice());
        intent.putExtra("Nights",       destinationsList.get(position).getNights());
        intent.putExtra("People",       destinationsList.get(position).getPeople());
        startActivity(intent);
        finish();
    }
}
