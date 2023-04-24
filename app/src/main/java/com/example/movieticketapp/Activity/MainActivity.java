package com.example.movieticketapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homePage:
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    break;
                case R.id.ticketPage:
                    Log.e("fd","fd");
                    startActivity(new Intent(MainActivity.this, BookedActivity.class));
                    break;

            }
            return true;
        });

        binding.bottomNavigation.getMenu().getItem(2).setChecked(true);
    }

//    private void rePlaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();
//    }
}