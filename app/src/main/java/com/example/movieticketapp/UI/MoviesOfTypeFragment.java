package com.example.movieticketapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieticketapp.Adapter.SliderAdapter;
import com.example.movieticketapp.PostItem;
import com.example.movieticketapp.R;

import java.util.List;

public class MoviesOfTypeFragment extends Fragment {
    private List<PostItem> listPost;

    private ViewPager2 viewPager;
    public MoviesOfTypeFragment(List<PostItem> listPost) {

        this.listPost = listPost;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewPager = view.findViewById(R.id.vpPosterSlider);
//        viewPager.setAdapter(new SliderAdapter(listPost, viewPager));
//        viewPager.setClipToPadding(false);
//        viewPager.setClipChildren(false);
//        viewPager.setOffscreenPageLimit(listPost.size());
//        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(24));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1- Math.abs(position);
//                page.setScaleY(0.65f + r * 0.15f);
//            }
//        });
//        viewPager.setPageTransformer(compositePageTransformer);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_of_type, container, false);
    }
}