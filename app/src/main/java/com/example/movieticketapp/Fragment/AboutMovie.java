package com.example.movieticketapp.Fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Booking.ShowTimeScheduleActivity;
import com.example.movieticketapp.Adapter.AddDecoration;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.VideoAdapter;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class AboutMovie extends Fragment {
    FilmModel film;


    RecyclerView videoListView;

    String[] videoList;
    public AboutMovie(FilmModel f) {
        // Required empty public constructor
        film = f;

    }
    public AboutMovie() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_about_movie, container, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView description = getView().findViewById(R.id.descriptionTV);
        description.setText(film.getDescription());
        Button BookBt = getView().findViewById(R.id.BookBt);

        if(film.getMovieBeginDate().toDate().after(Helper.getCurrentDate()) ||film.getMovieEndDate().toDate().before(Helper.getCurrentDate()) ) {
            BookBt.setVisibility(View.GONE);
        }
        else BookBt.setVisibility(View.VISIBLE);

        videoListView = getView().findViewById(R.id.videoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        VideoAdapter videoAdapter = new VideoAdapter();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference VideoRef = db.collection("Movies").document(film.getId());
        VideoRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    FilmModel filmModel = snapshot.toObject(FilmModel.class);
                    videoAdapter.setVideoIdList(filmModel.getTrailer());
                    videoListView.setAdapter(videoAdapter);
                    videoListView.setLayoutManager(linearLayoutManager);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    return;
//                }
//
//                videoList.clear();
//                for (QueryDocumentSnapshot doc : value)
//                {
//                    String videoURL = doc.getString("videoURL");
//
//                    videoList.add(videoURL);
//                    Log.d(TAG, "Added video with url: " + videoURL);
//                }
//
//            }
//        });



        try{

            if(Users.currentUser!=null)
                if(((Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                    BookBt.setText("Schedule");
                    BookBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            schedule();
                        }
                    });
                }
                else {
                    BookBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            book();
                        }
                    });
                }
        }
        catch (Exception e)
        {
            BookBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    book();
                }
            });
        }


    }

    void book()
    {
        Intent i = new Intent(getView().getContext(), BookedActivity.class);
        i.putExtra("selectedFilm", film);
        i.putExtra("nameFilm", film.getName());
        getView().getContext().startActivity(i);
    }
    void schedule()
    {
        Intent i = new Intent(getView().getContext(), ShowTimeScheduleActivity.class);
        i.putExtra("selectedFilm", film);
        i.putExtra("nameFilm", film.getName());
        getView().getContext().startActivity(i);
    }
}