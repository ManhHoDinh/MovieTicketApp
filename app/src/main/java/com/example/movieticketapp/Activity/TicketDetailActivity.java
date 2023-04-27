package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieticketapp.R;

public class TicketDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail_screen);
        TextView rateTV = (TextView) findViewById(R.id.vote);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        rateTV.setText("("+String.valueOf(ratingBar.getRating())+")");
        Button backButton = (Button) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}