package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class FilmReportAdapter extends RecyclerView.Adapter<FilmReportAdapter.ViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull FilmReportAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.IndexTV.setText(String.valueOf(position));
        Picasso.get().load(Films.get(position).getPosterImage()).into(holder.Poster);
        holder.FilmName.setText(Films.get(position).getName());

//        holder.DiscountRateTV.setText("OFF "+new DecimalFormat("#.0#").format(Discounts.get(position).getDiscountRate())+ "%");
        //if user

    }
    List<FilmModel> Films;

    public FilmReportAdapter(List<FilmModel> Films) {
        this.Films = Films;
    }

    @Override
    public int getItemCount() {
        return Films.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView IndexTV;
        ImageView Poster;
        TextView FilmName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // imageView = itemView.findViewById(R.id.posterItem);
            Poster=itemView.findViewById(R.id.PosterImage);
            IndexTV=itemView.findViewById(R.id.IndexTV);
            FilmName =itemView.findViewById(R.id.filmNameTV);
        }
    }
    @NonNull
    @Override
    public FilmReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_report_item, parent, false);
        return new FilmReportAdapter.ViewHolder(itemView);
    }
}
