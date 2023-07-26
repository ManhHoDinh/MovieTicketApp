package com.example.movieticketapp.Activity.Booking;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessCheckoutActivity extends AppCompatActivity {
    TextView backHome;
    Button myTicketBtn;
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
        setContentView(R.layout.success_checkout_screen);
        backHome= findViewById(R.id.txtToHome);
        myTicketBtn = (Button) findViewById(R.id.btnToMyTicket);
        InforBooked.getInstance().isDateSelected = false;
        InforBooked.getInstance().isTimeSelected = false;
        InforBooked.getInstance().isCitySelected = false;
        InforBooked.getInstance().timeBooked = "";
        InforBooked.getInstance().prevPosition = -1;
        myTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessCheckoutActivity.this, MyTicketAllActivity.class));
            }
        });
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