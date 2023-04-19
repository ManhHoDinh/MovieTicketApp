package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.MovieBookedAdapter;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity {
    private ListView listMovieBooked;
    private FloatingActionButton topUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        listMovieBooked = (ListView) findViewById(R.id.listMovieBooked);
        topUpBtn = (FloatingActionButton) findViewById(R.id.topUpBtn);
        List<MovieBooked> listMovie = new ArrayList<MovieBooked>();
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
        listMovieBooked.setAdapter(new MovieBookedAdapter(this, R.layout.movie_booked_item, listMovie ));
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWalletActivity.this, TopUpActivity.class);
                startActivity(intent);
            }
        });
    }
}