package com.example.movieticketapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieticketapp.Fragment.AboutMovie;
import com.example.movieticketapp.Fragment.ReviewFragment;
import com.example.movieticketapp.Model.FilmModel;

public class FilmDetailPagerAdapter extends FragmentStateAdapter {
    FilmModel film;
    int tabIndex;

    public FilmDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity, FilmModel f, int tabIndex) {
        super(fragmentActivity);
        film = f;
        this.tabIndex = tabIndex;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1)
            return new ReviewFragment(film);
        else
            return new AboutMovie(film);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
