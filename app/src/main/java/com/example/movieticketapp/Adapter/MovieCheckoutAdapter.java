package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieticketapp.Model.CheckoutFilmModel;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.List;

public class MovieCheckoutAdapter extends ArrayAdapter<CheckoutFilmModel> {
    private Context context;
    private int resource;
    private List<CheckoutFilmModel> movie;
    public MovieCheckoutAdapter(Context context, int resource, List<CheckoutFilmModel> movie)
    {
        super(context, resource, movie);
        this.resource = resource;
        this.movie = movie;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        CheckoutFilmModel movie = getItem(position);
        if (movie!=null) {
            TextView title = (TextView) v.findViewById(R.id.movieTitle);
            RatingBar rating = (RatingBar) v.findViewById(R.id.rating);
            TextView ratingPoint = (TextView) v.findViewById(R.id.ratingPoint);
            TextView genre = (TextView) v.findViewById(R.id.genre);
            TextView duration = (TextView) v.findViewById(R.id.duration);
            RoundedImageView poster = (RoundedImageView) v.findViewById(R.id.poster);

            if (title != null)
                title.setText(movie.getName());
            if (ratingPoint != null)
            {
                ratingPoint.setText("("+movie.getVote()+")");
                rating.setRating((float)movie.getVote());
            }
            if (genre != null)
                genre.setText(movie.getGenre());
            if (poster != null)
                poster.setImageResource(movie.getPoster());
            if (duration != null)
                duration.setText(movie.getDuration());
        }
        return v;
    }
}
