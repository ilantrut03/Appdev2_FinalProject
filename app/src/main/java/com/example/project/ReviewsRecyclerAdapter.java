package com.example.project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder> {
    private final ListenerInterface listener;
    Context reviewContext;
    ArrayList<MyReview> reviewList;

    public ReviewsRecyclerAdapter(ListenerInterface listener, Context reviewContext, ArrayList<MyReview> reviewList) {
        this.listener = listener;
        this.reviewContext = reviewContext;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_view, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerAdapter.ViewHolder holder, int position) {
        MyReview review = reviewList.get(position);
        holder.reviewName.setText(String.valueOf(review.getName()));
        //holder.reviewScore.setRating(Integer.parseInt(String.valueOf(review.getScore())));
        holder.reviewMessage.setText(String.valueOf(review.getText()));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName;
        RatingBar reviewScore;
        TextView reviewMessage;
        public ViewHolder(@NonNull View itemView, ListenerInterface listener) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.reviewNameText);
            reviewScore = itemView.findViewById(R.id.reviewRatingBar);
            reviewMessage = itemView.findViewById(R.id.reviewMessageText);

        }
    }
}