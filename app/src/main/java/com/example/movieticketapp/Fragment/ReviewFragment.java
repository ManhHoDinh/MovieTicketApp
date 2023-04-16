package com.example.movieticketapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
public class ReviewFragment extends Fragment {

    FilmModel film;

    public ReviewFragment( FilmModel f) {
        // Required empty public constructor
        film = f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }
}