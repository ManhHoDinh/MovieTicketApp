package com.example.movieticketapp.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.FilmDetailPagerAdapter;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class InformationFilmActivity extends FragmentActivity {
    ImageView backgroundImage;
    TextView nameTV;
    ImageView PosterImage;
    RatingBar ratingBar;
    TextView voteTV;
    TextView directorTV;
    TextView durationTime;
    TabLayout tabLayout;
    ViewPager2 pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        FilmModel f = intent.getParcelableExtra(ExtraIntent.film);
        setContentView(R.layout.information_film_screen);

        backgroundImage= findViewById(R.id.backgroundImage);
        backgroundImage.setImageResource(f.getBackGroundImage());

        nameTV= findViewById(R.id.filmName);
        nameTV.setText(f.getName());

        PosterImage= findViewById(R.id.PosterImage);
        PosterImage.setImageResource(f.getPosterImage());

        ratingBar = findViewById(R.id.rating);
        ratingBar.setRating((float) f.getVote());

        voteTV = findViewById(R.id.vote);
        voteTV.setText("(" + String.valueOf(f.getVote())+")");

        directorTV = findViewById(R.id.director);
        directorTV.setText(f.getDirector());

        durationTime = findViewById(R.id.durationTime);
        durationTime.setText(f.getDurationTime());

        tabLayout=findViewById(R.id.tab_layout);
        pager=findViewById(R.id.pager);

        pager.setAdapter(new FilmDetailPagerAdapter(this));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}