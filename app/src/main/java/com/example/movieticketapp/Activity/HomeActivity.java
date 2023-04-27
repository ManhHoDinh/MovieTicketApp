package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.movieticketapp.Activity.Account.AccountActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.posterAdapter;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
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
    private HomeScreenBinding binding;
    private ImageView accountImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountImage = findViewById(R.id.accountImage);
        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(i);
            }
        });
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
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.walletPage:
                    startActivity(new Intent(HomeActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(HomeActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });
    }


}
