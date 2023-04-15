package com.example.movieticketapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieticketapp.Fragment.AboutMovie;
import com.example.movieticketapp.Fragment.ReviewFragment;

public class FilmDetailPagerAdapter extends FragmentStateAdapter {
    public FilmDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new ReviewFragment();
        else
            return new AboutMovie();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
