package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookSeatActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameFilmTv;
    private TextView nameCinemaTv;
    private TextView countTicketTv;
    private TextView priceTv;
    private Button backBtn;
    private Button SeatBookBtn;
    ViewGroup layout;
    ViewGroup layout1;

    String seats =
            "AAAAAA_AAAAAA_AAAAAA/"
                    + "AABBAA_AAABBA_AAAAAA/"
                    + "AAAAAA_AAAAAA_AABBAA/"
                    + "ABBBAA_AAAAAA_AAAAAA/"
                    + "__AAAA_AAAAAA_AAAA__/"
                    + "__AAAA_AAAAAA_ABAA__/"
                    + "__AAAA_AABBAA_AAAA__/"
                    + "__AAAA_AAAAAA_AAAA__/"
                    + "__ABBA_AAAABA_AAAA__/"
                    + "__BAAA_AABAAA_AAAA__/"
            ;


    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    List<String> selectedIds = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);
        Intent intent = getIntent();
        nameFilmTv = (TextView) findViewById(R.id.nameFilm);
        nameCinemaTv = (TextView) findViewById(R.id.nameCinema);
        nameFilmTv.setText(intent.getStringExtra("nameFirm"));
        nameCinemaTv.setText(intent.getStringExtra("nameCinema"));
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
                //i.putExtra(ExtraIntent.film, film);
                startActivity(i);
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
        priceTv.setText(50000 * selectedIds.size() +" VNĐ");
    }
}