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
        DocumentReference docRef = FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        InforBooked.getInstance().total += Integer.parseInt(String.valueOf(document.get("wallet")));
                        totalTv.setText(String.valueOf(document.get("Wallet")));

                    } else {
                        Log.e("c", "No such document");
                    }
                } else {
                    Log.e("dđ", "get failed with ", task.getException());
                }
            }
        });
        loadListMovieBooked();
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
            }
            return true;
        });

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        movieBookedAdapter.startListening();
//    }


    void loadListMovieBooked(){


//        FirestoreRecyclerOptions<MovieBooked> options = new FirestoreRecyclerOptions.Builder<MovieBooked>()
//                .setQuery(query, MovieBooked.class)
//                .build();
//        movieBookedAdapter = new MovieBookedAdapter(options, progressBar);
//        listMovieBooked.setAdapter(movieBookedAdapter);
       // listMovieBooked.setLayoutManager(new LinearLayoutManager(MyWalletActivity.this, LinearLayoutManager.VERTICAL, false));
//        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                listMovie = value.toObjects(MovieBooked.class);
//
//            }
//
//        });

             firestore.collection("BookedMovie").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : listDoc){
                        if(doc.get("UserID").equals(FirebaseRequest.mAuth.getUid())){
                            setListBookedMovie(String.valueOf(doc.get("MovieID")));
//                            Log.e("ff", FirebaseRequest.mAuth.getUid());
                        }

                    }


//                    movieBookedAdapter = new MovieBookedAdapter(getApplicationContext(), R.layout.movie_booked_item, listMovie );
//                    listMovieBooked.setAdapter(movieBookedAdapter);
                    //progressBar.setVisibility(View.VISIBLE);

                }
            }

        });
    }
    void setListBookedMovie(String movieID){
        firestore.collection("Ticket").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : listDoc){

                        if(doc.getId().equals(movieID)) {

                            Ticket film = doc.toObject(Ticket.class);

                            listMovie.add(film);
                        }
                    }
                    movieBookedAdapter = new MovieBookedAdapter(getApplicationContext(), R.layout.movie_booked_item, listMovie );
                    listMovieBooked.setAdapter(movieBookedAdapter);
                }
            }

        });


        }
}