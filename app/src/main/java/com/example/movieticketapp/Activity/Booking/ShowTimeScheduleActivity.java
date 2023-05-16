package com.example.movieticketapp.Activity.Booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityShowTimeScheduleBinding;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowTimeScheduleActivity extends AppCompatActivity {
    ActivityShowTimeScheduleBinding binding;
    private List<Stgring> listCity;
    private AutoCompleteTextView countryAutoTv;
    private RecyclerView dayRecycleView;
    private FirebaseFirestore firestore;
    private ListView cinemaLv;
    private ImageButton nextBtn;
    private TextView nameFilmTv;
    private FilmModel selectedFilm;
    private Button backBtn;
    protected static String binhdd;
    private String monthName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowTimeScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        backBtn = (Button) findViewById(R.id.backbutton);
        firestore = FirebaseFirestore.getInstance();
        countryAutoTv = (AutoCompleteTextView) findViewById(R.id.countryAutoTv);
        listCity = new ArrayList<String>();
        loadListCity();

        dayRecycleView = (RecyclerView) findViewById(R.id.dayRecycleView);
        Calendar calendar = Calendar.getInstance();
        String[]dateName={"SAT", "SUN", "MON","TUE","WED", "THU", "FRI", };
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
        TimeBookedAdapter timeBookedAdapter = new TimeBookedAdapter(listDate, listTime, null, null);

        dayRecycleView.setAdapter(new TimeBookedAdapter(listDate, listTime, null, null));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dayRecycleView.setLayoutManager(layoutManager);

        cinemaLv = (ListView) findViewById(R.id.cinemaLv);

        // String[] listCinemaName = {"Central Park CGV", "FX Sudirman XXI", "Kelapa Gading IMAX"};

        nameFilmTv = (TextView) findViewById(R.id.nameFilmtv);
        nameFilmTv.setText(selectedFilm.getName());
        nextBtn = (ImageButton) findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InforBooked.getInstance().dateBooked == null || InforBooked.getInstance().timeBooked == null ){
                    Toast.makeText(ShowTimeScheduleActivity.this, "Please choote time and date!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ShowTimeScheduleActivity.this, BookSeatActivity.class);
                    intent.putExtra("selectedFilm", selectedFilm);
                    intent.putExtra("nameCinema", InforBooked.getInstance().nameCinema);
                    intent.putExtra("dateBooked", InforBooked.getInstance().dateBooked);
                    intent.putExtra("timeBooked", InforBooked.getInstance().timeBooked);
                    startActivity(intent);
                }

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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



                    List<String> listCinemaName = new ArrayList<String>();
                    FirebaseRequest.database.collection("Cinema").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc : listDocs){
                                if(doc.get("CityID").equals(list.get(i).getID())){
                                    listCinemaName.add(String.valueOf(doc.get("Name")));

                                }
                            }
                            CinameNameAdapter cinameNameAdapter = new CinameNameAdapter(ShowTimeScheduleActivity.this, R.layout.cinema_booked_item,listCinemaName, selectedFilm.getName());
                            cinemaLv.setAdapter(cinameNameAdapter);
                            cinemaLv.setEnabled(false);
                            Log.e("ff", "gg");
                            Helper.getListViewSize(cinemaLv);
                        }
                    });
                }
            });
            }
            }
}
        );
    }

    void loadListCinema(){


    }

}