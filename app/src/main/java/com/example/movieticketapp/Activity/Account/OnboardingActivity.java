package com.example.movieticketapp.Activity.Account;

import static com.example.movieticketapp.R.string.CheckUsed;

import android.content.Intent;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;

public class OnboardingActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_screen);
        SetSharedReference();
        Button getStartBt = findViewById(R.id.getStartedBtn);
        getStartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
        TextView signIn=findViewById(R.id.SignInBtn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }
        });

    }
    void SetSharedReference()
    {
        SharedPreferences sharedPref = getSharedPreferences("shared_prefs",0);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.clear();
        editor.putBoolean(String.valueOf(CheckUsed), true);
        editor.apply();
        editor.commit();
    }
}
