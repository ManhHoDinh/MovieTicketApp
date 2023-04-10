package com.example.movieticketapp.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieticketapp.R;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_screen);
        //ImageView logo = findViewById(R.id.logo);
        //logo.setImageResource(R.drawable.splash_logo);
        Button getStartBt = findViewById(R.id.getStartedBtn);
        getStartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });
        TextView signIn=findViewById(R.id.SignInBtn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(i);
            }
        });
    }
}