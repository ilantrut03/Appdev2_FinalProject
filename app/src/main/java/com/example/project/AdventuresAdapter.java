package com.example.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdventuresAdapter extends RecyclerView.Adapter<AdventuresAdapter.ViewHolder> {

    private final ListenerInterface listener;
    Context destinationContext;
    ArrayList<Destination> destinationsList;

    public AdventuresAdapter(Context destinationContext, ArrayList<Destination> destinationsList, ListenerInterface listener) {        // new
        this.destinationContext = destinationContext;
        this.destinationsList = destinationsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adventures, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationsList.get(position);
        holder.destinationName.setText(String.valueOf(destination.getName()));
        holder.destinationPrice.setText(String.valueOf(destination.getPrice()));
        holder.destinationNights.setText("" + destination.getNights());
        Glide.with(destinationContext).load(destination.getImageUrl()).into(holder.destinationImage);
    }

    @Override
    public int getItemCount() { return destinationsList.size(); }

    public void filterGiftNameList(ArrayList<Destination> filteredDestinationNameList) {
        destinationsList = filteredDestinationNameList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView destinationImage;
        TextView destinationName, destinationPrice, destinationNights;
        Button discover;

        public ViewHolder(@NonNull View itemView, ListenerInterface listener) {
            super(itemView);
            destinationName     = itemView.findViewById(R.id.destinationNameView);
            destinationImage    = itemView.findViewById(R.id.destinationImageView);
            destinationPrice    = itemView.findViewById(R.id.destinationPriceView);
            destinationNights   = itemView.findViewById(R.id.destinationNightsView);
            discover            = itemView.findViewById(R.id.button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) { listener.onItemClick(position); }
                    }
                }
            });
        }
    }
}