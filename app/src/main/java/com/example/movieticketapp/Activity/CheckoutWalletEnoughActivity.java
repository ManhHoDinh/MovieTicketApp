package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.movieticketapp.R;

public class CheckoutWalletEnoughActivity extends AppCompatActivity {
    ImageView BtnBack;
    Button BtnCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_wallet_enough_screen);
        BtnBack = findViewById(R.id.btnBack);
        BtnCheckOut= findViewById(R.id.btnCheckout);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        BtnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SuccessCheckoutActivity.class);
                //i.putExtra(ExtraIntent.film, film);
                startActivity(i);
            }
        });
    }
}