package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;

import java.util.ArrayList;

public class MyTicketExpiredActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Ticket> arrayList = new ArrayList<Ticket>();
    TicketListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ticket_expired_screen);
        listView = (ListView) findViewById(R.id.ListViewTicket3);

        arrayList.add(new Ticket("Ralph Breaks the Internet 1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

        adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
        listView.setAdapter(adapter);
        Button newTicket = (Button) findViewById(R.id.buttonNewsTicket);
        newTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyTicketNewsActivity.class);
                startActivity(i);
            }
        });
        Button allTicket = (Button) findViewById(R.id.buttonAllTicket);
        allTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyTicketAllActivity.class);
                startActivity(i);
            }
        });
    }
}