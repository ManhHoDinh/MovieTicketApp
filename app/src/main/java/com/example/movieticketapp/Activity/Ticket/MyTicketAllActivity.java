package com.example.movieticketapp.Activity.Ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

//import com.example.movieticketapp.databinding.ActivityHomeBinding;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.example.movieticketapp.databinding.MyTicketAllScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyTicketAllActivity extends AppCompatActivity {
    ListView listView;
    FirebaseFirestore firestore;
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
        firestore = FirebaseFirestore.getInstance();

        allTicket.setText("All");
        allTicket.setSelected(true);

        loadListTicket();


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

                loadListTicket();


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

                loadListTicket();


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
                loadListTicket();


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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = listView.getItemAtPosition(i);
                Ticket ticket = (Ticket) o;
                Intent a = new Intent(getApplicationContext(),TicketDetailActivity.class);
                a.putExtra("name", ((Ticket) o).getName());
                a.putExtra("time", ((Ticket) o).getTime());
                a.putExtra("cinema", ((Ticket) o).getCinema());
                a.putExtra("poster", ((Ticket) o).getPoster());
                a.putExtra("rate", ((Ticket) o).getRate());
                a.putExtra("kind", ((Ticket) o).getKind());
                a.putExtra("duration", ((Ticket) o).getDuration());
                a.putExtra("seat", ((Ticket) o).getSeat());
                a.putExtra("paid", ((Ticket) o).getPaid());
                a.putExtra("idorder", ((Ticket) o).getIdorder());
                startActivity(a);
            }
        });

    }

    void loadListTicket() {
        firestore.collection("Ticket").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : listDoc){
                        Ticket _ticket = doc.toObject(Ticket.class);
                        arrayList.add(_ticket);
                    }

                    adapter = new TicketListAdapter(getApplicationContext(), R.layout.list_ticket_view, arrayList);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}