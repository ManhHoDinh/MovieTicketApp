package com.example.movieticketapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private ViewPager2 typeMoviePage;
    private BottomNavigationView bottomNavigationView;
    private TabLayout typeMovieLayout;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewPager = (ViewPager2) view.findViewById(R.id.viewPagePosterSlider);
//
//        List<PostItem> listPosts = new ArrayList<PostItem>();
//        listPosts.add(new PostItem(R.drawable.movie_poster, "bìnhhhhh"));
//        listPosts.add(new PostItem(R.drawable.avatar, "bìnhhh"));
//        listPosts.add(new PostItem(R.drawable.control, "bìnhhh"));
//        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
//        viewPager.setClipToPadding(false);
//        viewPager.setClipChildren(false);
//        viewPager.setOffscreenPageLimit(3);
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
        // searchview
//        typeMovieLayout = (TabLayout) view.findViewById(R.id.tabViewTypeMove);
//        typeMoviePage = (ViewPager2) view.findViewById(R.id.typeMovieViewPage);
//        typeMoviePage.setAdapter(new TypeMovieViewPageAdapter(this));
//        typeMovieLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                typeMoviePage.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}