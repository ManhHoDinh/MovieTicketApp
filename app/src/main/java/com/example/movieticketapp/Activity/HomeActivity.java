package com.example.movieticketapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.AddDecoration;
import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.SliderAdapter;
import com.example.movieticketapp.Adapter.posterAdapter;
import com.example.movieticketapp.MainActivity;
import com.example.movieticketapp.PostItem;
import com.example.movieticketapp.R;
import com.example.movieticketapp.UI.TypeMovieViewPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;
    private RecyclerView typeListView;
    private RecyclerView posterRecyclerView;

    private ViewPager2 typeMoviePage;
    private BottomNavigationView bottomNavigationView;
    private TabLayout typeMovieLayout;
    private BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String[] listType = {"All","Honor", "Action", "Drama", "War", "Comedy", "Crime"};
        List<Integer> listPoster = new ArrayList<Integer>();
        listPoster.add(R.drawable.poster_1);
        listPoster.add(R.drawable.poster_1);
        listPoster.add(R.drawable.poster_1);
        posterRecyclerView = (RecyclerView) findViewById(R.id.commingMovieView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        posterRecyclerView.setAdapter(new posterAdapter(listPoster));
        posterRecyclerView.setLayoutManager(linearLayoutManager);

        typeListView = (RecyclerView) findViewById(R.id.listTypeMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeListView.setLayoutManager(layoutManager);
        typeListView.addItemDecoration(new AddDecoration(10));
        typeListView.setAdapter(new ListTypeAdapter(this, listType));



//        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
//        bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.ticketPage:
//                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            }
//            return true;
//        });

//        typeMovieLayout = (TabLayout) findViewById(R.id.tabViewTypeMove);
//        typeMoviePage = (ViewPager2)  findViewById(R.id.typeMovieViewPage);
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
}