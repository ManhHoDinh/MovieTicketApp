package com.example.movieticketapp.Activity.Movie;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.FilmDetailPagerAdapter;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InformationFilmActivity extends FragmentActivity {
    ImageView backgroundImage;
    TextView nameTV;
    ImageView PosterImage;
    RatingBar ratingBar;
    TextView voteTV;
    TextView genreTV;
    TextView durationTime;
    TabLayout tabLayout;
    ViewPager2 pager;
    FilmModel f;

    FilmDetailPagerAdapter filmDetailPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_film_screen);
        Intent intent = getIntent();
        f = intent.getParcelableExtra(ExtraIntent.film);
        backgroundImage = findViewById(R.id.backgroundImage);
        nameTV= findViewById(R.id.filmName);
        PosterImage= findViewById(R.id.PosterImage);
        ratingBar = findViewById(R.id.rating);
        voteTV = findViewById(R.id.vote);
        genreTV = findViewById(R.id.genre);
        durationTime = findViewById(R.id.durationTime);
        ImageView btnBack = findViewById(R.id.btnBack);
        pager=findViewById(R.id.pager);
        tabLayout=findViewById(R.id.tab_layout);
        filmDetailPagerAdapter = new FilmDetailPagerAdapter(this, f);
        pager.setAdapter(filmDetailPagerAdapter);
        pager.setOffscreenPageLimit(3);
        getFilm(f.getId());

        refreshScreen();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
    void getFilm(String id)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Movies").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) { if (error != null) {
                    refreshScreen();
                    return;
                }


                if (snapshot != null && snapshot.exists()) {
                    f = snapshot.toObject(FilmModel.class);
                    refreshScreen();
                }
            }
        });
    }
    void refreshScreen()
    {
        nameTV.setText(f.getName());

        Picasso.get().load(f.getBackGroundImage()).fit().centerCrop().into(backgroundImage);

        Picasso.get().load(f.getPosterImage()).fit().centerCrop().into(PosterImage);

        ratingBar.setRating(Float.parseFloat(f.getVote()));

        voteTV.setText("(" + String.valueOf(f.getVote())+")");

        genreTV.setText(f.getGenre());

        durationTime.setText(f.getDurationTime());
    }
}