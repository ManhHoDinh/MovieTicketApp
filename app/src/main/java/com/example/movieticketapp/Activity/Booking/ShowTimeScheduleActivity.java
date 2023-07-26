package com.example.movieticketapp.Activity.Booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.CinameNameAdapter;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.TimeBookedAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.ScheduleFilm;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityShowTimeScheduleBinding;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowTimeScheduleActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private List<String> listCity;
    private AutoCompleteTextView countryAutoTv;
    private RecyclerView dayRecycleView;
    private FirebaseFirestore firestore;
    private ListView cinemaLv;
    private Button createBtn;

    private TextView nameFilmTv;
    private FilmModel selectedFilm;
    private Button backBtn;

    private String monthName;
    private CinameNameAdapter cinameNameAdapter;
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
//        ScheduleFilm.getInstance().listShowTime = new ArrayList<ShowTime>();
//        ScheduleFilm.getInstance().isDateSelected = false;
//        ScheduleFilm.getInstance().isCitySelected = false;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_schedule);
        backBtn = (Button) findViewById(R.id.backbutton);
        firestore = FirebaseFirestore.getInstance();
        countryAutoTv = (AutoCompleteTextView) findViewById(R.id.countryAutoTv);
        createBtn = findViewById(R.id.createShowTimeBtn);
        listCity = new ArrayList<String>();
        loadListCity();

        dayRecycleView = (RecyclerView) findViewById(R.id.dayRecycleView);
        Calendar calendar = Calendar.getInstance();

//        Timestamp time = new Timestamp(calendar.getTime());
//        Log.e("ff", (time + 1).toDate().toString());
        String[]dateName={"Sat", "Sun", "Mon","Tue","Wed", "Thu", "Fri", };
        List<String> listDate = new ArrayList<String>();
        List<String> listTime = new ArrayList<String>();
        //Toast.makeText(this, String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) , Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        selectedFilm = intent.getParcelableExtra("selectedFilm");
        int countDate = calendar.get(Calendar.DAY_OF_WEEK);
        int countTime = calendar.get(Calendar.DATE);
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < 13; i++){
            if (countDate > 6) countDate = countDate - 7;
            if (countTime > dayOfMonth) {
                countTime = 1;

            }
            listDate.add(dateName[countDate]);
            listTime.add(String.valueOf(countTime));
            countDate++;
            countTime++;
        }
        cinemaLv = (ListView) findViewById(R.id.cinemaLv);
        TimeBookedAdapter timeBookedAdapter = new TimeBookedAdapter(listDate, listTime, selectedFilm, null, null, cinemaLv, ShowTimeScheduleActivity.this);

        dayRecycleView.setAdapter(new TimeBookedAdapter(listDate, listTime, selectedFilm, null, null, cinemaLv, ShowTimeScheduleActivity.this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dayRecycleView.setLayoutManager(layoutManager);
        // String[] listCinemaName = {"Central Park CGV", "FX Sudirman XXI", "Kelapa Gading IMAX"};

        nameFilmTv = (TextView) findViewById(R.id.nameFilmtv);
        nameFilmTv.setText(selectedFilm.getName());


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ScheduleFilm.getInstance().listShowTime.size() == 0){
                    Toast.makeText(ShowTimeScheduleActivity.this, "Please choose showtime!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Dialog confirmDialog = new Dialog(ShowTimeScheduleActivity.this);
                    confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    confirmDialog.setContentView(R.layout.activity_confirm_dialog);
                    Window window = confirmDialog.getWindow();
                    if(window != null){
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        WindowManager.LayoutParams windowAttribute = window.getAttributes();
                        windowAttribute.gravity = Gravity.CENTER;
                        window.setAttributes(windowAttribute);
                        confirmDialog.show();
                        TextView confirmTv = confirmDialog.findViewById(R.id.confirmTv);
                        TextView cancelTv = confirmDialog.findViewById(R.id.cancelTv);
                        Log.e("te", ScheduleFilm.getInstance().listShowTime.size()+"");
                        confirmTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for(ShowTime showTime : ScheduleFilm.getInstance().listShowTime){
                                    FirebaseRequest.database.collection("Showtime").document().set(showTime);

                                }

                                ScheduleFilm.getInstance().listShowTime = new ArrayList<ShowTime>();
                                loadListCity();
//                            ScheduleFilm.getInstance().isCitySelected = false;
//                            ScheduleFilm.getInstance().isDateSelected = false;
                                Toast.makeText(ShowTimeScheduleActivity.this, "Schedule show time successfully!", Toast.LENGTH_SHORT).show();
                                confirmDialog.dismiss();


                            }
                        });
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                confirmDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleFilm.getInstance().listShowTime = new ArrayList<ShowTime>();
                ScheduleFilm.getInstance().isDateSelected = false;
                ScheduleFilm.getInstance().isCitySelected = false;
                finish();
            }
        });
    }

    void loadListCity(){
        firestore.collection("City").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<City> list = new ArrayList<City>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : listDoc) {
                        list.add(doc.toObject(City.class));
                        listCity.add(String.valueOf(doc.get("Name")));

                    }
                    countryAutoTv.setAdapter(new ArrayAdapter<String>(ShowTimeScheduleActivity.this, R.layout.country_item, listCity));
                    countryAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_background_1)));

                    countryAutoTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ScheduleFilm.getInstance().isCitySelected = true;
                            List<Cinema> listCinema = new ArrayList<Cinema>();
                            FirebaseRequest.database.collection("Cinema").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot doc : listDocs){
                                        if(doc.get("CityID").equals(list.get(i).getID())){
                                            listCinema.add(doc.toObject(Cinema.class));
                                        }
                                    }

                                    cinameNameAdapter = new CinameNameAdapter(ShowTimeScheduleActivity.this, R.layout.cinema_booked_item,listCinema, selectedFilm);
                                    cinemaLv.setAdapter(cinameNameAdapter);







                                }
                            });
                        }
                    });
                }
            }
        }
    );
    }

    @Override
    public void onBackPressed() {
        ScheduleFilm.getInstance().listShowTime = new ArrayList<ShowTime>();
        ScheduleFilm.getInstance().isDateSelected = false;
        ScheduleFilm.getInstance().isCitySelected = false;
        super.onBackPressed();
    }

    void loadListCinema(){


    }

}