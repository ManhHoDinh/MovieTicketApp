package com.example.movieticketapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieticketapp.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.homePage:
//                    rePlaceFragment(new HomeFragment());
//                    break;
//            }
//            return true;
//        });
//        rePlaceFragment(new HomeFragment());
//    }
//    private void rePlaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();
//    }
}