package com.example.movieticketapp.Activity.Report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.movieticketapp.Activity.Booking.ShowTimeScheduleActivity;
import com.example.movieticketapp.Activity.Discount.DiscountViewAll;
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Notification.NotificationActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.CinameNameAdapter;
import com.example.movieticketapp.Adapter.FilmReportAdapter;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Adapter.TimeScheduleAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private  BottomNavigationView bottomNavigationView;
    private AutoCompleteTextView CinemaAutoTv;
    private AutoCompleteTextView MonthAutoTv;
    private AutoCompleteTextView YearAutoTv;
    private FirebaseFirestore firestore;
    private CollectionReference MovieRef;
    private List<FilmModel> films= new ArrayList<>();
    ImageButton control;
    TextView TotalPrice;
    List<String> cinemaNames = new ArrayList<>();
    String selectedCinema="All Cinema";
    int SelectedMonth = 0;
    int SelectedYear = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_screen);
        firestore = FirebaseFirestore.getInstance();
        control = findViewById(R.id.controlBtn);
        MovieRef = firestore.collection("Movies");
        TotalPrice=findViewById(R.id.totalPrice);
        ControlButton();
        LoadCinema();
        LoadFilms();
        LoadMonth();
        LoadYear();
        BottomNavigation();
        Search();
    }

    private void ControlButton() {
        ConstraintLayout fliter = findViewById(R.id.Filter);
        //Init  Layout
        ViewGroup.LayoutParams params = fliter.getLayoutParams();
        params.height = 0;
        fliter.setLayoutParams(params);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fliter.getVisibility()==View.VISIBLE) {
                    fliter.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = fliter.getLayoutParams();
                    params.height = 0;
                    fliter.setLayoutParams(params);
                }
                else{
                    fliter.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = fliter.getLayoutParams();
                    params.height = 200;
                    fliter.setLayoutParams(params);
                }

            }
        });

    }

    void LoadCinema()
    {
        cinemaNames.add("All Cinema");
        CinemaAutoTv = findViewById(R.id.CinemaFilter);

        FirebaseRequest.database.collection("Cinema").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> listDocs = value.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    Cinema cinema = doc.toObject(Cinema.class);
                    cinemaNames.add(cinema.getName());
                   }
                CinemaAutoTv.setAdapter(new ArrayAdapter<String>(ReportActivity.this, R.layout.dropdown_item, cinemaNames));
                CinemaAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color)));
            }
        });

        CinemaAutoTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCinema = parent.getItemAtPosition(position).toString();

                LoadFilms();
            }
        });
    }
    void LoadMonth()
    {
        List<String> months =new ArrayList<>();
        months.add("Month");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        MonthAutoTv = findViewById(R.id.MonthFilter);
        MonthAutoTv.setAdapter(new ArrayAdapter<String>(ReportActivity.this, R.layout.dropdown_item, months));
        MonthAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color)));
        MonthAutoTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectMonth = parent.getItemAtPosition(position).toString();
                SelectedMonth= months.indexOf(selectMonth);
                LoadFilms();
            }
        });
    }
    void LoadYear()
    {
        List<String> Years =new ArrayList<>();
        Years.add("Year");
        Years.add("2022");
        Years.add("2023");
        Years.add("2024");

        YearAutoTv = findViewById(R.id.YearFilter);
        YearAutoTv.setAdapter(new ArrayAdapter<String>(ReportActivity.this, R.layout.dropdown_item, Years));
        YearAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color)));
        YearAutoTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectYear = parent.getItemAtPosition(position).toString();

                if(selectYear.equals("Year"))
                    SelectedYear=0;
                else
                    SelectedYear= Integer.parseInt(selectYear);
                LoadFilms();
            }
        });
    }
    void  LoadFilms()
    {
        RecyclerView filmReports=findViewById(R.id.FilmReports);
        //filmReports.setAdapter();
        MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                films.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    FilmModel f = documentSnapshot.toObject(FilmModel.class);
                    films.add(f);
                }
                LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                filmReports.setLayoutManager(VerLayoutManager);
                FilmReportAdapter filmReportAdapter = new FilmReportAdapter(films, selectedCinema,SelectedMonth, SelectedYear);
                filmReports.setAdapter(filmReportAdapter);

                filmReportAdapter.setOnDataChangedListener(new FilmReportAdapter.OnDataChangedListener() {
                    @Override
                    public void onDataChanged(int totalPrice) {
                        TotalPrice.setText(String.valueOf(totalPrice));
                    }
                });
            }
        });
    }
    void BottomNavigation()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().findItem(R.id.ReportPage).setChecked(true);
        try{if (Users.currentUser != null)
            if (((Users.currentUser.getAccountType().toString()).equals("admin"))) {
                Menu menu = bottomNavigationView.getMenu();
                MenuItem ReportPage = menu.findItem(R.id.ReportPage);
                MenuItem WalletPage = menu.findItem(R.id.walletPage);
                WalletPage.setVisible(false);
                ReportPage.setVisible(true);
            }
        }
        catch (Exception e){}
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePage:
                    startActivity(new Intent(ReportActivity.this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(ReportActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.NotificationPage:
                    startActivity(new Intent(ReportActivity.this, NotificationActivity.class));
                    overridePendingTransition(0, 0);
                    break;
            }
            return true;
        });
    }
    void Search()
    {
        SearchView Search = findViewById(R.id.searchField);

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        LinearLayout layoutElement = findViewById(R.id.ReportLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        RecyclerView filmReports=findViewById(R.id.FilmReports);
        //filmReports.setAdapter();
        MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                films.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    FilmModel f = documentSnapshot.toObject(FilmModel.class);
                    if (f.getName().toLowerCase().contains(text.toLowerCase()))
                        films.add(f);
                }
                LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                filmReports.setLayoutManager(VerLayoutManager);
                filmReports.setAdapter(new FilmReportAdapter(films, selectedCinema,SelectedMonth, SelectedYear));
            }
        });

    }
}