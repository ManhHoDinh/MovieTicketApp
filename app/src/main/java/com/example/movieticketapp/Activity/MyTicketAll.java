package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;

import java.util.ArrayList;

public class MyTicketAll extends AppCompatActivity {
    ListView listView;
    ArrayList<Ticket> arrayList = new ArrayList<Ticket>();
    TicketListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_all);
        listView = (ListView) findViewById(R.id.ListViewTicket);

        arrayList.add(new Ticket("Ralph Breaks the Internet 1", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 2", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 3", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 4", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 5", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));
        arrayList.add(new Ticket("Ralph Breaks the Internet 6", "16:40, Sun May 22", "FX Sudirman XXI", R.drawable.poster_ralph));

        adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
        listView.setAdapter(adapter);
    }
}