package com.example.movieticketapp.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.movieticketapp.R;

public class PrefixSignInActivity extends AppCompatActivity {
    Button user;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefix_sign_in_screen);
        user = (Button) findViewById(R.id.userlogin);
        admin = (Button) findViewById(R.id.adminlogin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("type", "user");
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("type", "admin");
                startActivity(intent);
            }
        });
    }
}