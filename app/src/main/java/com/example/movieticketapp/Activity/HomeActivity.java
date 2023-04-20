package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.posterAdapter;
import com.example.movieticketapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
public class HomeActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;
    private RecyclerView typeListView;
    private RecyclerView posterRecyclerView;
    private SearchView searchView;
    private ViewPager2 typeMoviePage;
    private BottomNavigationView bottomNavigationView;
    private TabLayout typeMovieLayout;
    private BottomNavigationView bottomNavigation;
    //private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // binding = ActivityHomeBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        String[] listType = {"All", "Honor", "Action", "Drama", "War", "Comedy", "Crime"};
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
        //  typeListView.addItemDecoration(new AddDecoration(10));
        typeListView.setAdapter(new ListTypeAdapter(this, listType));
//        binding.bottomNavigation.setOnItemReselectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.walletPage:
//                    startActivity(new Intent(HomeActivity.this, OnboardingActivity.class));
//                    break;
//                case R.id.ticketPage:
//                    startActivity(new Intent(HomeActivity.this, MyTicketAllActivity.class));
//                    break;
//            }
//            return ;
//        });


//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.walletPage:
//                    startActivity(new Intent(HomeActivity.this, MyWalletActivity.class));
//                    break;
//            }
//            return true;
//        });
    }


}
