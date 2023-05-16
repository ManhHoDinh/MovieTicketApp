package com.example.movieticketapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Booking.ShowTimeScheduleActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;

public class AboutMovie extends Fragment {
    FilmModel film;
    public AboutMovie(FilmModel f) {
        // Required empty public constructor
        film = f;
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
        try{
            Log.d("account type", Users.currentUser.getAccountType());
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
    }void schedule()
    {
        Intent i = new Intent(getView().getContext(), ShowTimeScheduleActivity.class);
        i.putExtra("selectedFilm", film);
        i.putExtra("nameFilm", film.getName());
        getView().getContext().startActivity(i);
    }
}