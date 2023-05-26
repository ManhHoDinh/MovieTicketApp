package com.example.movieticketapp.Activity.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Booking.CheckoutWalletEnoughActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookSeatActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameFilmTv;
    private TextView nameCinemaTv;
    private TextView countTicketTv;
    private TextView priceTv;
    private Button backBtn;
    private Button SeatBookBtn;
    private int initPrice;
    private String nameCinema;

    ViewGroup layout;
    ViewGroup layout1;

    String seats =
             "AAAAAA_AAAAAA_AAAAAA/"
            +"AAAAAA_AAAAAA_AAAAAA/"
            +"AAAAAA_AAAAAA_AAAAAA/"
            +"AAAAAA_AAAAAA_AAAAAA/"
            +"__AAAA_AAAAAA_AAAA__/"
            +"__AAAA_AAAAAA_AAAA__/"
            +"__AAAA_AAAAAA_AAAA__/"
            +"__AAAA_AAAAAA_AAAA__/"
            +"__AAAA_AAAAAA_AAAA__/"
            +"__AAAA_AAAAAA_AAAA__/"
                ;


    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    private FilmModel selectedFilm;
    private String dateBooked;
    private String timeBooked;
    List<String> selectedIds = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);
        Intent intent = getIntent();
        timeBooked = intent.getStringExtra("timeBooked");
        dateBooked = intent.getStringExtra("dateBooked");
        selectedFilm = intent.getParcelableExtra("selectedFilm");
        nameFilmTv = (TextView) findViewById(R.id.nameFilm);
        nameCinemaTv = (TextView) findViewById(R.id.nameCinema);
        nameFilmTv.setText(selectedFilm.getName());
        nameCinema = intent.getStringExtra("nameCinema");
        nameCinemaTv.setText(nameCinema);

        countTicketTv = (TextView) findViewById(R.id.countTicketTv);
        priceTv = (TextView) findViewById(R.id.priceTv);
        backBtn = (Button) findViewById(R.id.backbutton);
        layout = findViewById(R.id.layoutSeat);
        //layout1 = findViewById(R.id.layoutBinh);
        SeatBookBtn = findViewById(R.id.SeatBookBt);


        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        seats = "/" + seats;
        char alphabet = 'A';
        int count = 0;
        for (int index = 0; index < seats.length(); index++)
        {

                 if (seats.charAt(index) == '_')
                 {
                    if(count %18 == 14) count = count + 4;
                    TextView view = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                    view.setLayoutParams(layoutParams);
                    view.setBackgroundColor(Color.TRANSPARENT);
                    view.setText("");
                    layout.addView(view);
                }
                 else if (seats.charAt(index) == '/')
                 {
                         layout = new LinearLayout(this);
                         layout.setOrientation(LinearLayout.HORIZONTAL);
                         layoutSeat.addView(layout);
                 }
                 else
                 {
                     count++;

                     TextView view = new TextView(this);
                     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                     layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                     view.setLayoutParams(layoutParams);
                     view.setPadding(0, 0, 0, 2 * seatGaping);
                     view.setId(count);
                     view.setGravity(Gravity.CENTER);
                     view.setTextColor(Color.WHITE);
                     view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                     if (seats.charAt(index) == 'U') {
                         view.setBackgroundResource(R.drawable.your_state);
                         view.setTag(STATUS_BOOKED);
                     } else if (seats.charAt(index) == 'A') {
                         view.setBackgroundResource(R.drawable.available_state);
                         view.setTag(STATUS_AVAILABLE);
                     } else if (seats.charAt(index) == 'B') {
                         view.setBackgroundResource(R.drawable.booked_state);
                         view.setTag(STATUS_RESERVED);
                     }
                     layout.addView(view);
                     seatViewList.add(view);
                     view.setOnClickListener(this);
                     if(count % 18 != 0)
                     view.setText(String.valueOf((char) (alphabet + count/18)) + (count %18) +"");
                     else view.setText(String.valueOf((char) (alphabet + count/18 - 1)) + 18 +"");



                 }
                 loadSeats();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layout1 = layoutSeat;
        SeatBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CheckoutWalletEnoughActivity.class);
                if(priceTv.getText().equals(") VNĐ")){
                    Toast.makeText(BookSeatActivity.this, "Please choose seats!", Toast.LENGTH_SHORT).show();
                }
                else{

                    i.putExtra("dateBooked", dateBooked);
                    i.putExtra("timeBooked", timeBooked);
                    i.putExtra("selectedFilm", selectedFilm);
                    i.putExtra("cinemaName",nameCinemaTv.getText().toString());
                    i.putExtra("price",priceTv.getText());
                    i.putExtra("price",priceTv.getText());
                    i.putStringArrayListExtra("seats", (ArrayList<String>) selectedIds);
                    startActivity(i);
                }

            }
        });
    }
    void loadSeats(){
        FirebaseRequest.database.collection("showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    Timestamp time = doc.getTimestamp("timeBooked");
                    DateFormat dateFormat = new SimpleDateFormat("EEE\ndd");
                    DateFormat timeFormat = new SimpleDateFormat("H:m");
                    if(doc.get("nameCinema").equals(nameCinema)
                            && timeFormat.format(time.toDate()).equals(timeBooked)
                            && dateFormat.format(time.toDate()).equals(dateBooked)
                            && doc.get("nameFilm").equals(selectedFilm.getName())){
                        List<String> bookedSeats = (List<String>) doc.get("bookedSeat");
                        LinearLayout linearLayout =(LinearLayout) layout.getChildAt(0);
                        for(int i = 0; i < 10; i ++){
                            LinearLayout layout = (LinearLayout) linearLayout.getChildAt(i);
                            for(int j = 0; j < 20; j ++){
                                TextView view = (TextView) layout.getChildAt(j);
                                for(int z = 0; z < bookedSeats.size(); z++){
                                    if(bookedSeats.get(z).equals(view.getText().toString())){
                                        view.setBackgroundResource(R.drawable.booked_state);
                                        view.setTag(STATUS_RESERVED);

                                    }
                                }
                            }
                        }

                    }
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        if ((int) view.getTag() == STATUS_AVAILABLE) {
            view.setTag(STATUS_BOOKED);
            view.setBackgroundResource(R.drawable.your_state);
            selectedIds.add(((TextView) view).getText().toString());




        } else if ((int) view.getTag() == STATUS_BOOKED) {
            view.setTag(STATUS_AVAILABLE);
            view.setBackgroundResource(R.drawable.available_state);
            selectedIds.remove(((TextView) view).getText().toString());

//
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + ((TextView) view).getText().toString() + " is Reserved", Toast.LENGTH_SHORT).show();

        }
        countTicketTv.setText("Total Price ("+ selectedIds.size()+ " Ticket)");
        FirebaseRequest.database.collection("Cinema").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    if(doc.get("Name").equals(nameCinema)){
                        initPrice = Integer.parseInt(String.valueOf(doc.get("Price")));

                        priceTv.setText(initPrice * selectedIds.size() +" VNĐ");
                    }
                }

            }
        });

    }
}