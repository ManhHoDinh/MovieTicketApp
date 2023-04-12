package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;

public class InformationFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        FilmModel f = intent.getParcelableExtra(ExtraIntent.film);
        setContentView(R.layout.information_film_screen);
        ImageView img= findViewById(R.id.backgroundImage);
        img.setImageResource(R.drawable.background_image);
        TextView tv = findViewById(R.id.filmName);
        tv.setText(f.getDirector());
    }
}