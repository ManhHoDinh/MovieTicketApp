package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.MovieBookedAdapter;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity {
    ListView listMovieBooked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        listMovieBooked = (ListView) findViewById(R.id.listMovieBooked);
        List<MovieBooked> listMovie = new ArrayList<MovieBooked>();
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovieBooked.setAdapter(new MovieBookedAdapter(this, R.layout.movie_booked_item, listMovie ));
    }
}