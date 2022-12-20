package com.example.project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>  {

    private final ListenerInterface listener;
    Context favContext;
    ArrayList<Destination> favoritesList;

    public FavoritesAdapter(Context favContext, ArrayList<Destination> favoritesList, ListenerInterface listener) {
        this.favContext = favContext;
        this.favoritesList = favoritesList;
        this.listener = listener;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_view, parent, false);
        return new FavoritesAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination favDestination = favoritesList.get(position);
        holder.destinationName.setText(String.valueOf(favDestination.getName()));
        holder.destinationPrice.setText(String.valueOf(favDestination.getPrice()));
        holder.destinationPrice.append("$");
        Glide.with(favContext).load(favDestination.getImageUrl()).into(holder.destinationImage);
    }


    @Override
    public int getItemCount() {
            return favoritesList.size();
            }

    public void filterGiftNameList(ArrayList<Destination> filteredDestNameList) {
        favoritesList = filteredDestNameList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView destinationName;
        TextView destinationPrice;
        ImageView destinationImage;

        public ViewHolder(@NonNull View itemView, ListenerInterface listener) {
            super(itemView);
            destinationName = itemView.findViewById(R.id.favNameView);
            destinationPrice = itemView.findViewById(R.id.favPriceView);
            destinationImage = itemView.findViewById(R.id.favImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}