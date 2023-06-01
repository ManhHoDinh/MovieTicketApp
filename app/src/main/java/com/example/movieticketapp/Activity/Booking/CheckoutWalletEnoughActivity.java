package com.example.movieticketapp.Activity.Booking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Booking.SuccessCheckoutActivity;
import com.example.movieticketapp.Activity.Discount.DiscountViewAll;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheckoutWalletEnoughActivity extends AppCompatActivity {
    ImageView BtnBack;
    private String idDiscount;
    Button BtnCheckOut;
    TextView totalTv;
    ListView movieInfoView;
    TextView cinemaNameTv;
    TextView dateTimeTv;
    TextView seatTv;
    TextView priceTv;
    TextView totalService;
    Button selectVoucherBtn;
    TextView yourWallet;
    TextView idOrder;
    FilmModel film;
    LinearLayout checkoutinforView;
    double total;
    String date;
    TextView selectTv;
    String timeBooked;
    String cinemaName;
    String[] listDate;
    String listSeat;
    String price;
    MovieCheckoutAdapter adapter;
    List<String> seats;
    ArrayList<CheckoutFilmModel> movie = new ArrayList<>();
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null){

                    total = result.getData().getDoubleExtra("total", 0);
                    String name = result.getData().getStringExtra("nameDiscount");
                    idDiscount = result.getData().getStringExtra("idDiscount");
                    selectTv.setVisibility(View.GONE);
                    selectVoucherBtn.setText(name);
                    totalTv.setText(String.valueOf(Math.round(total)) + " VNĐ");

//                    String title = result.getData().getStringExtra("title") ;
//                    String date = result.getData().getStringExtra("date") ;
//                    boolean isDone = result.getData().getBooleanExtra("checked", false) ;



                }
            }
        }
    });
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
        selectVoucherBtn = findViewById(R.id.selectVoucherBtn);
        totalService = findViewById(R.id.totalService);
        selectTv = findViewById(R.id.selectTv);
        movieInfoView = findViewById(R.id.movieInfoView);
        checkoutinforView = findViewById(R.id.checkoutinfo);
        selectVoucherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutWalletEnoughActivity.this, DiscountViewAll.class);
                intent.putExtra("total", total);
                startForResult.launch(intent);
            }
        });

        FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                yourWallet.setText(String.valueOf(doc.get("Wallet")));
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        film = bundle.getParcelable("selectedFilm");
       // film = intent.getParcelableExtra("selectedFilm");
        cinemaName =bundle.getString("cinemaName");
       // cinemaName =intent.getStringExtra("cinemaName");
        cinemaNameTv.setText(cinemaName);
        date = bundle.getString("dateBooked");
       // date = intent.getStringExtra("dateBooked");
        timeBooked = bundle.getString("timeBooked");
        totalService.setText(bundle.getString("total service"));
       // timeBooked = intent.getStringExtra("timeBooked");
        listDate = date.split("\n");
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());
        dateTimeTv.setText( listDate[0] + " "+month_name+" " + listDate[1]+", " + timeBooked);
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
        seats = bundle.getStringArrayList("seats");
        //seats = intent.getStringArrayListExtra("seats");
        listSeat = "";
        for(int i = 0; i < seats.size(); i++){
            if(i != seats.size() - 1){
                listSeat += seats.get(i) + ", ";
            }
            else {
                listSeat += seats.get(i) ;

            }

        }
        price = bundle.getString("price");
        total = Integer.parseInt(price) + Integer.parseInt(bundle.getString("total service"));
        //price = intent.getStringExtra("price");
        int initPrice = Integer.parseInt(price) / seats.size();
        priceTv.setText(initPrice + " x " + seats.size());
        seatTv.setText(listSeat);
        totalTv.setText(String.valueOf(Math.round(total)) + " VNĐ");
        movie.add(new CheckoutFilmModel(film.getName(),film.getVote(), film.getGenre(),film.getDurationTime(), film.getPosterImage()));
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
                        if(Math.round(total) <= totalWallet){
                            totalWallet -= Math.round(total);
                            if(idDiscount != null){
                                FirebaseRequest.database.collection("Discounts").document(idDiscount).delete();

                            }
                            FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).update("Wallet", totalWallet);
                            FirebaseRequest.database.collection("showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot doc : listDocs){
                                        Timestamp time = doc.getTimestamp("timeBooked");
                                        DateFormat dateFormat = new SimpleDateFormat("EEE\nd");
                                        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                        DateFormat monthformat = new SimpleDateFormat("MMM");
                                        String month_name = monthformat.format(time.toDate());
                                      //  String timeBook = timeBooked + ", " + listDate[0] + " "+month_name+" " + listDate[1];


                                        if(doc.get("nameCinema").equals(cinemaName) && doc.get("nameFilm").equals(film.getName()) && timeFormat.format(time.toDate()).equals(timeBooked) && dateFormat.format(time.toDate()).equals(date)){
                                            List<String> listSeats = (List<String>) doc.get("bookedSeat");

                                            for(int i = 0; i < seats.size(); i++){
                                                listSeats.add(seats.get(i));
                                                FirebaseRequest.database.collection("showtime").document(doc.getId()).update("bookedSeat", listSeats);
                                            }
//                                            Ticket ticket = new Ticket(film.getName(),time,
//                                                    cinemaName,film.getPosterImage(),
//                                                    Double.parseDouble(film.getVote()),
//                                                    film.getGenre(), film.getDurationTime(), listSeat, price, idOrder.getText().toString() );
                                            Ticket ticket = new Ticket(time, cinemaName, listSeat, String.valueOf(Math.round(total)) + " VNĐ", idOrder.getText().toString(), film.getId(), FirebaseRequest.mAuth.getUid());
                                            FirebaseRequest.database.collection("Ticket").document().set(ticket);

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