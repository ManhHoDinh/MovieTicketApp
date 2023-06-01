package com.example.movieticketapp.Adapter;

import static androidx.fragment.app.FragmentManager.TAG;

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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class FilmReportAdapter extends RecyclerView.Adapter<FilmReportAdapter.ViewHolder> {
    FirebaseFirestore firestore ;

    @Override
    public void onBindViewHolder(@NonNull FilmReportAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.IndexTV.setText(String.valueOf(position));
        Picasso.get().load(Films.get(position).getPosterImage()).into(holder.Poster);
        holder.FilmName.setText(Films.get(position).getName());
        GetTotalTick(holder.TotalTicket,Films.get(position));
    }
    List<FilmModel> Films;

    public FilmReportAdapter(List<FilmModel> Films) {
        this.Films = Films;
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return Films.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView IndexTV;
        ImageView Poster;
        TextView FilmName;
        TextView TotalTicket;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // imageView = itemView.findViewById(R.id.posterItem);
            Poster=itemView.findViewById(R.id.PosterImage);
            IndexTV=itemView.findViewById(R.id.IndexTV);
            FilmName =itemView.findViewById(R.id.filmNameTV);
            TotalTicket=itemView.findViewById(R.id.TotalTicketTV);
        }
    }
    @NonNull
    @Override
    public FilmReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_report_item, parent, false);
        return new FilmReportAdapter.ViewHolder(itemView);
    }
    int result = 0;

    void GetTotalTick(TextView textView,FilmModel film) {
        firestore.collection("Ticket").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            result=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ticket s = document.toObject(Ticket.class);
                                if ((s.getFilmID()).equals(film.getName()))
                                {
                                    String seat= s.getSeat();
                                    int count = 1;
                                    count +=seat.length() - s.getSeat().replace(",", "").length();
                                    result += count;
                                }
                                textView.setText("Ticket: "+String.valueOf(result));
                            }
                        } else {
                        }
                    }
                });
    }
}
