package com.example.movieticketapp.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieticketapp.PostItem;
import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class TypeMovieViewPageAdapter extends FragmentStateAdapter {
    public TypeMovieViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<PostItem> list = new ArrayList<PostItem>();
        list.add(new PostItem(R.drawable.movie_poster, "f"));
        list.add(new PostItem(R.drawable.movie_poster, "f"));
        list.add(new PostItem(R.drawable.movie_poster, "f"));
        switch (position){
            case 0:
                return new MoviesOfTypeFragment(list);
            case 1:
                return new MoviesOfTypeFragment(list);

            default: return new MoviesOfTypeFragment(list);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
