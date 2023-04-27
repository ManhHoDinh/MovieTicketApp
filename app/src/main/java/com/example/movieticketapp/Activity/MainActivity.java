package com.example.movieticketapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieticketapp.R;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//        startActivity(intent);
        setContentView(R.layout.main_screen);
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