package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.MovieBookedAdapter;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.MovieBooked;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity {
    private ListView listMovieBooked;
    private FloatingActionButton topUpBtn;
    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore firestore;
    private TextView totalTv;
    private ProgressBar progressBar;
    List<MovieBooked> listMovie;
    private MovieBookedAdapter movieBookedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        listMovieBooked = (ListView) findViewById(R.id.listMovieBooked);
        topUpBtn = (FloatingActionButton) findViewById(R.id.topUpBtn);
        firestore = FirebaseFirestore.getInstance();
        listMovie = new ArrayList<MovieBooked>();
        totalTv = (TextView) findViewById(R.id.total);

        totalTv.setText(String.valueOf(InforBooked.getInstance().total));
        progressBar = (ProgressBar) findViewById(R.id.progressId);
        progressBar.setVisibility(View.GONE);
//        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
//        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
//        listMovie.add(new MovieBooked("binh", 10000,"30/3/2032", R.drawable.poster_1));
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

    void loadListMovieBooked(){
            firestore.collection("BookedMovie").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : listDoc){
                        MovieBooked movieBooked = doc.toObject(MovieBooked.class);
                        listMovie.add(movieBooked);

                    }

                    movieBookedAdapter = new MovieBookedAdapter(getApplicationContext(), R.layout.movie_booked_item, listMovie );
                    listMovieBooked.setAdapter(movieBookedAdapter);
                    //progressBar.setVisibility(View.VISIBLE);

                }
            }

        });
    }
}