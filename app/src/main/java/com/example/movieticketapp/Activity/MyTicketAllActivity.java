package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

//import com.example.movieticketapp.databinding.ActivityHomeBinding;

import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.example.movieticketapp.databinding.MyTicketAllScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MyTicketAllActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Ticket> arrayList = new ArrayList<Ticket>();
    TicketListAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ticket_all_screen);
        listView = (ListView) findViewById(R.id.ListViewTicket);
        Button newTicket = (Button) findViewById(R.id.buttonNewsTicket);
        Button expiredTicket = (Button) findViewById(R.id.buttonExpiredTicket);
        Button allTicket = (Button) findViewById(R.id.buttonAllTicket);
        BottomNavigationView abc = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        abc.getMenu().getItem(2).setChecked(true);

        allTicket.setText("All");
        allTicket.setSelected(true);

        arrayList.add(new Ticket("Ralph Breaks the Internet 1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 3", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

        adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
        listView.setAdapter(adapter);

        allTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTicket.setSelected(true);
                newTicket.setSelected(false);
                expiredTicket.setSelected(false);
                allTicket.setText("All");
                newTicket.setText(null);
                expiredTicket.setText(null);
                arrayList.clear();
                arrayList.add(new Ticket("Ralph Breaks the Internet X1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
                arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
                arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

                adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
                listView.setAdapter(adapter);
            }
        });
        newTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTicket.setSelected(false);
                newTicket.setSelected(true);
                expiredTicket.setSelected(false);
                allTicket.setText(null);
                newTicket.setText("News");
                expiredTicket.setText(null);
                arrayList.clear();
                arrayList.add(new Ticket("Ralph Breaks the Internet 1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
                arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

                adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
                listView.setAdapter(adapter);
            }
        });

        expiredTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTicket.setSelected(false);
                newTicket.setSelected(false);
                expiredTicket.setSelected(true);
                allTicket.setText(null);
                newTicket.setText(null);
                expiredTicket.setText("Expired");
                arrayList.clear();
                arrayList.add(new Ticket("Ralph Breaks the Internet 1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

                adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
                listView.setAdapter(adapter);
            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePage:
                    startActivity(new Intent(MyTicketAllActivity.this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.walletPage:
                    startActivity(new Intent(MyTicketAllActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

    }
}