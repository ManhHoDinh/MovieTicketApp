package com.example.movieticketapp.Activity.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Booking.SuccessCheckoutActivity;
import com.example.movieticketapp.Adapter.MovieCheckoutAdapter;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.CheckoutFilmModel;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheckoutWalletEnoughActivity extends AppCompatActivity {
    ImageView BtnBack;
    Button BtnCheckOut;
    TextView totalTv;
    ListView movieInfoView;
    TextView cinemaNameTv;
    TextView dateTimeTv;
    TextView seatTv;
    TextView priceTv;
    TextView yourWallet;
    TextView idOrder;
    FilmModel film;
    int total;
    String date;
    String time;
    String cinemaName;
    String[] listDate;
    String listSeat;
    String price;
    MovieCheckoutAdapter adapter;
    List<String> seats;
    ArrayList<CheckoutFilmModel> movie = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_wallet_enough_screen);
        BtnBack = findViewById(R.id.btnBack);
        BtnCheckOut= findViewById(R.id.btnCheckout);
        totalTv = (TextView) findViewById(R.id.TotalValue) ;
        cinemaNameTv = (TextView) findViewById(R.id.CinemaValue) ;
        dateTimeTv = (TextView) findViewById(R.id.DateTimeValue) ;
        priceTv = (TextView) findViewById(R.id.PriceValue) ;
        seatTv = (TextView) findViewById(R.id.SeatNumberValue) ;
        yourWallet = (TextView) findViewById(R.id.YourWalletValue) ;
        idOrder = (TextView) findViewById(R.id.IDOrderValue);
        movieInfoView = findViewById(R.id.movieInfoView);
        FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                yourWallet.setText(String.valueOf(doc.get("Wallet")));
            }
        });
        Intent intent = getIntent();
        film = intent.getParcelableExtra("selectedFilm");
        cinemaName =intent.getStringExtra("cinemaName");
        cinemaNameTv.setText(cinemaName);
        date = intent.getStringExtra("dateBooked");
        time = intent.getStringExtra("timeBooked");
        listDate = date.split("\n");
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());
        dateTimeTv.setText( listDate[0] + " "+month_name+" " + listDate[1]+", " + time);
        Random rdn = new Random();

        FirebaseRequest.database.collection("Ticket").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                int id = 0;
                for(DocumentSnapshot doc: listDocs){
                    do{
                        id = rdn.nextInt(90000000) + 10000000;
                    }
                    while (
                            id == Integer.parseInt(String.valueOf(doc.get("idorder")))
                    );
                }
                idOrder.setText(String.valueOf(id));
            }
        });
        int id;
        seats = intent.getStringArrayListExtra("seats");
        listSeat = "";
        for(int i = 0; i < seats.size(); i++){
            if(i != seats.size() - 1){
                listSeat += seats.get(i) + ", ";
            }
            else {
                listSeat += seats.get(i) ;

            }

        }

        price = intent.getStringExtra("price");
        int initPrice = Integer.parseInt(price.substring(0,price.length() -4)) / seats.size();
        priceTv.setText(initPrice + " x " + seats.size());
        seatTv.setText(listSeat);
        totalTv.setText(price);
        movie.add(new CheckoutFilmModel("Ralph Breaks the Internet",4.7, "Action & adventure, Comedy","1h 41min", R.drawable.poster_ralph));
        adapter = new MovieCheckoutAdapter(getApplicationContext(), R.layout.checkout_movie_view, movie);
        movieInfoView.setAdapter(adapter);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        BtnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        int totalWallet = Integer.parseInt(String.valueOf(doc.get("Wallet")));
                        if(Integer.parseInt(price.substring(0, price.length() - 4)) <= totalWallet){
                            totalWallet -= Integer.parseInt(price.substring(0, price.length() - 4));
                            FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).update("Wallet", totalWallet);
                            String timeBook = time + ", " + listDate[0] + " "+month_name+" " + listDate[1];
                            Ticket ticket = new Ticket(film.getName(),timeBook,
                                    cinemaName,film.getPosterImage(),
                                    Double.parseDouble(film.getVote()),
                                    film.getGenre(), film.getDurationTime(), listSeat, price, idOrder.getText().toString() );
                            FirebaseRequest.database.collection("Ticket").document().set(ticket);
                            FirebaseRequest.database.collection("showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot doc : listDocs){
                                        if(doc.get("NameCinema").equals(cinemaName)){
                                            List<String> listSeats = (List<String>) doc.get("BookedSeat");

                                            for(int i = 0; i < seats.size(); i++){
                                                listSeats.add(seats.get(i));
                                                FirebaseRequest.database.collection("showtime").document(doc.getId()).update("BookedSeat", listSeats);
                                            }

                                        }
                                    }
                                }
                            });
                            Intent i = new Intent(getApplicationContext(), SuccessCheckoutActivity.class);
                            //i.putExtra(ExtraIntent.film, film);
                            startActivity(i);
                        }
                        else
                            Toast.makeText(CheckoutWalletEnoughActivity.this, "Your wallet is not enough!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}