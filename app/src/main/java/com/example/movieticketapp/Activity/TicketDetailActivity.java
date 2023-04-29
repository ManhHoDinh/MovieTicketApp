package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail_screen);
        RoundedImageView filmPoster = (RoundedImageView) findViewById(R.id.ivPoster);
        TextView filmName = (TextView) findViewById(R.id.tvName);
        RatingBar filmRating = (RatingBar) findViewById(R.id.rating);
        TextView filmRatingText = (TextView) findViewById(R.id.vote);
        TextView filmKind = (TextView) findViewById(R.id.tvKind);
        TextView filmDuration = (TextView) findViewById(R.id.tvDuration);
        TextView cinema = (TextView) findViewById(R.id.cinema);
        TextView bookDay = (TextView) findViewById(R.id.DateAndTime);
        TextView seatPositon = (TextView) findViewById(R.id.SeatNumber);
        TextView paidBill = (TextView) findViewById(R.id.Paid);
        TextView idOder = (TextView) findViewById(R.id.IDOrder);
        Button backButton = (Button) findViewById(R.id.backbutton);

        Picasso.get().load(getIntent().getExtras().getString("poster")).into(filmPoster);
        filmName.setText(getIntent().getExtras().getString("name"));
        filmRating.setRating((float) getIntent().getExtras().getDouble("rate"));
        filmRatingText.setText("(" + getIntent().getExtras().getDouble("rate") + ")");
        filmKind.setText(getIntent().getExtras().getString("kind"));
        filmDuration.setText(getIntent().getExtras().getString("duration"));
        cinema.setText(getIntent().getExtras().getString("cinema"));
        bookDay.setText(getIntent().getExtras().getString("time"));
        seatPositon.setText(getIntent().getExtras().getString("seat"));
        paidBill.setText(getIntent().getExtras().getString("paid"));
        idOder.setText(getIntent().getExtras().getString("idorder"));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}