package com.example.movieticketapp.Activity;

import com.example.movieticketapp.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuccessCheckoutActivity extends AppCompatActivity {
    TextView backHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_checkout_screen);
        backHome= findViewById(R.id.txtToHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                //i.putExtra(ExtraIntent.film, film);
                startActivity(i);
            }
        });
    }
}