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

import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

//public class MovieBookedAdapter extends FirestoreRecyclerAdapter<MovieBooked, MovieBookedAdapter.movieHolder> {
//
//    private ProgressBar progressBar;
//
//    public MovieBookedAdapter(@NonNull FirestoreRecyclerOptions<MovieBooked> options, ProgressBar progressBar) {
//        super(options);
//        this.progressBar = progressBar;
//    }
//
//    public MovieBookedAdapter(@NonNull FirestoreRecyclerOptions<MovieBooked> options) {
//        super(options);
//
//    }
//
//    public class movieHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView nameMovie;
//        TextView priceMovie;
//        TextView timeBooked;
//
//        /////ADD Film TO Film Information
//        public movieHolder(@NonNull View itemView) {
//            super(itemView);
//
//
//            imageView = itemView.findViewById(R.id.imageMovie);
//            nameMovie = itemView.findViewById(R.id.nameMovie);
//            priceMovie = itemView.findViewById(R.id.priceMovie);
//            timeBooked = itemView.findViewById(R.id.timeBooked);
//        }
//    }
//
//    @NonNull
//    @Override
//    public MovieBookedAdapter.movieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_booked_item, parent, false);
//        return new movieHolder(view);
//    }
//
//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//
//        if (progressBar != null)
//            progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull movieHolder holder, int position, @NonNull MovieBooked model) {
//        Picasso.get().load(model.getImageMovie()).into(holder.imageView);
//
//        holder.nameMovie.setText(model.getName());
//        holder.priceMovie.setText(String.valueOf(model.getPrice()));
//        holder.timeBooked.setText(model.getTimeBooked());
//
//    }
//
//}
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
        ImageView imageView = itemView.findViewById(R.id.imageMovie);
        TextView nameMovie = itemView.findViewById(R.id.nameMovie);
        TextView priceMovie = itemView.findViewById(R.id.priceMovie);
        TextView timeBooked = itemView.findViewById(R.id.timeBooked);
        Ticket movie = getItem(position);
//        Picasso.get().load(movie.getPoster()).into(imageView);
//
//        nameMovie.setText(movie.getName());
        priceMovie.setText(String.valueOf(movie.getPaid()));
        Timestamp time = movie.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm, E MMM dd");
        timeBooked.setText(dateFormat.format(time.toDate()));
        return itemView;
    }

}

