package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MovieBookedAdapter extends ArrayAdapter<MovieBooked> {
    private List<MovieBooked> listMovieBooked;


    public MovieBookedAdapter(@NonNull Context context, int resource, List<MovieBooked> listMovieBooked) {
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
        MovieBooked movie = getItem(position);
        Picasso.get().load(movie.getImageMovie()).into(imageView);

        nameMovie.setText(movie.getName());
        priceMovie.setText(String.valueOf(movie.getPrice()));
        timeBooked.setText(movie.getTimeBooked());
        return itemView;
    }

}

