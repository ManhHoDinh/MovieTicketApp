package com.example.movieticketapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.example.movieticketapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.splash_logo);
        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                Intent i = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(i);
                // close this activity

                finish();

            }

        }, 5*1000); // wait for 5 seconds
    }



}