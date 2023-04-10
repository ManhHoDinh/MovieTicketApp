package com.example.movieticketapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_screen);
        getSupportActionBar().hide();
        //ImageView logo = findViewById(R.id.logo);
        //logo.setImageResource(R.drawable.splash_logo);
        Button getStartBt = findViewById(R.id.button);
        getStartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(i);
            }
        });
    }
}