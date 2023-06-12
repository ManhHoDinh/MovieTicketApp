package com.example.movieticketapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class MovieBookedAdapter extends ArrayAdapter<Ticket> {
    private List<Ticket> listMovieBooked;


    public MovieBookedAdapter(@NonNull Context context, int resource, List<Ticket> listMovieBooked) {
        super(context, resource, listMovieBooked);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.movie_booked_item, null);
        RoundedImageView imageView = itemView.findViewById(R.id.imageMovie);
        TextView nameMovie = itemView.findViewById(R.id.nameMovie);
        TextView priceMovie = itemView.findViewById(R.id.priceMovie);
        TextView timeBooked = itemView.findViewById(R.id.timeBooked);
        Ticket ticket = getItem(position);
        DocumentReference doc = FirebaseRequest.database.collection("Movies").document(ticket.getFilmID());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FilmModel film = documentSnapshot.toObject(FilmModel.class);
                Picasso.get().load(film.getPosterImage()).into(imageView);
                nameMovie.setText(film.getName());
            }
        });

        priceMovie.setText(String.valueOf(ticket.getPaid()));
        Timestamp time = ticket.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm, E MMM dd");
        timeBooked.setText(dateFormat.format(time.toDate()));
        return itemView;
    }

}

