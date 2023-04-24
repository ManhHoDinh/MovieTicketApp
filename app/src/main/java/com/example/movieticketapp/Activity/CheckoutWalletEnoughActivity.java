package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.MovieCheckoutAdapter;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.CheckoutFilmModel;
import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class CheckoutWalletEnoughActivity extends AppCompatActivity {
    ImageView BtnBack;
    Button BtnCheckOut;

    ListView movieInfoView;

    MovieCheckoutAdapter adapter;
    ArrayList<CheckoutFilmModel> movie = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_wallet_enough_screen);
        BtnBack = findViewById(R.id.btnBack);
        BtnCheckOut= findViewById(R.id.btnCheckout);

        movieInfoView = findViewById(R.id.movieInfoView);

        movie.add(new CheckoutFilmModel("Ralph Breaks the Internet",4.7, "Action & adventure, Comedy","1h 41min", R.drawable.poster_ralph));
        adapter = new MovieCheckoutAdapter(getApplicationContext(), R.layout.checkout_movie_view, movie);
        movieInfoView.setAdapter(adapter);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        BtnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SuccessCheckoutActivity.class);
                //i.putExtra(ExtraIntent.film, film);
                startActivity(i);
            }
        });
    }
}