package com.example.movieticketapp.Activity.Wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.R;

public class SuccessTopUpActivity extends AppCompatActivity {
    private Button backWalletBtn;
    private TextView backHomeTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_top_up);
        backWalletBtn = (Button) findViewById(R.id.backWalletBtn);
        backHomeTv = (TextView) findViewById(R.id.backHomeTv);
        backWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessTopUpActivity.this, MyWalletActivity.class));
            }
        });
        backHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessTopUpActivity.this, HomeActivity.class));
            }
        });
    }
}