package com.example.movieticketapp.Activity.Wallet;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Notification.NotificationActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Adapter.MovieBookedAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity {

    private ListView listMovieBooked;
    private FloatingActionButton topUpBtn;
    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore firestore;
    private TextView totalTv;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private TextView nameUser;
    List<Ticket> listMovie;
    private MovieBookedAdapter movieBookedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        listMovieBooked = (ListView) findViewById(R.id.listMovieBooked);
        topUpBtn = (FloatingActionButton) findViewById(R.id.topUpBtn);
        firestore = FirebaseFirestore.getInstance();
        listMovie = new ArrayList<Ticket>();
        totalTv = (TextView) findViewById(R.id.total);
        nameUser = findViewById(R.id.nameUser);
        DocumentReference docRef = FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        InforBooked.getInstance().total += Integer.parseInt(String.valueOf(document.get("wallet")));
                        NumberFormat formatter = new DecimalFormat("#,###");
                        totalTv.setText(formatter.format(document.get("Wallet")) + " VNĐ");
                        nameUser.setText(String.valueOf(document.get("Name")))
                        ;

                    } else {
                        Log.e("c", "No such document");
                    }
                } else {
                    Log.e("dđ", "get failed with ", task.getException());
                }
            }
        });
        setListBookedMovie();
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWalletActivity.this, TopUpActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePage:
                    startActivity(new Intent(MyWalletActivity.this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(MyWalletActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.NotificationPage:
                    startActivity(new Intent(MyWalletActivity.this, NotificationActivity.class));
                    overridePendingTransition(0, 0);
                    break;

            }
            return true;
        });

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        movieBookedAdapter.startListening();
//    }



    void setListBookedMovie(){
        firestore.collection("Ticket").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot doc : value){
                    Ticket ticket = doc.toObject(Ticket.class);
                        if(ticket.getUserID().equals(FirebaseRequest.mAuth.getUid())){
                            listMovie.add(ticket);
                        }
                }
                movieBookedAdapter = new MovieBookedAdapter(getApplicationContext(), R.layout.movie_booked_item, listMovie );
                listMovieBooked.setAdapter(movieBookedAdapter);
            }
        });



        }
}