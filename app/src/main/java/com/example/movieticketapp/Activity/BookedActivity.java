package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.CinameNameAdapter;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.TimeBookedAdapter;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookedActivity extends AppCompatActivity {

    private String[] listCountry;
    private AutoCompleteTextView countryAutoTv;
    private RecyclerView dayRecycleView;
    private ListView cinemaLv;
    private ImageButton nextBtn;
    private TextView nameFilmTv;
    protected static String binhdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);
        countryAutoTv = (AutoCompleteTextView) findViewById(R.id.countryAutoTv);
        listCountry = new String[]{"binh", "dep", "trai"};
        countryAutoTv.setAdapter(new ArrayAdapter<String>(this, R.layout.country_item, listCountry));
        countryAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_background_1)));
        dayRecycleView = (RecyclerView) findViewById(R.id.dayRecycleView);
        Calendar calendar = Calendar.getInstance();
        String[]dateName={"SAT", "SUN", "MON","TUE","WED", "THU", "FRI", };
        List<String> listDate = new ArrayList<String>();
        List<String> listTime = new ArrayList<String>();
        //Toast.makeText(this, String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) , Toast.LENGTH_LONG).show();

        int countDate = calendar.get(Calendar.DAY_OF_WEEK);
        int countTime = calendar.get(Calendar.DATE);
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < 13; i++){
            if (countDate > 6) countDate = countDate - 7;
            if (countTime > dayOfMonth) countTime = 1;
            listDate.add(dateName[countDate]);
            listTime.add(String.valueOf(countTime));
            countDate++;
            countTime++;
        }
        TimeBookedAdapter timeBookedAdapter = new TimeBookedAdapter(listDate, listTime, null, null);

        dayRecycleView.setAdapter(new TimeBookedAdapter(listDate, listTime, null, null));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dayRecycleView.setLayoutManager(layoutManager);

        cinemaLv = (ListView) findViewById(R.id.cinemaLv);
        String[] listCinemaName = {"Central Park CGV", "FX Sudirman XXI", "Kelapa Gading IMAX"};
        CinameNameAdapter cinameNameAdapter = new  CinameNameAdapter(this, R.layout.cinema_booked_item,listCinemaName);
        cinemaLv.setAdapter(cinameNameAdapter);
        cinemaLv.setEnabled(false);
        Helper.getListViewSize(cinemaLv);
        nameFilmTv = (TextView) findViewById(R.id.nameFilmtv);
        nameFilmTv.setText("Ralph Breaks the Internet");
        nextBtn = (ImageButton) findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookedActivity.this, BookSeatActivity.class);
                intent.putExtra("nameFirm", nameFilmTv.getText().toString());
                intent.putExtra("nameCinema", InforBooked.getInstance().nameCinema);
                intent.putExtra("dateBooked", InforBooked.getInstance().dateBooked);
                intent.putExtra("timeBooked", InforBooked.getInstance().timeBooked);
                startActivity(intent);
            }
        });

    }
}