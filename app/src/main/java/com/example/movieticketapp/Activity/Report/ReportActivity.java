package com.example.movieticketapp.Activity.Report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.CinameNameAdapter;
import com.example.movieticketapp.Adapter.FilmReportAdapter;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.FilmModel;
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
    private FirebaseFirestore firestore;
    private CollectionReference MovieRef;
    private List<FilmModel> films= new ArrayList<>();
    ImageButton control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_screen);
        firestore = FirebaseFirestore.getInstance();
        control = findViewById(R.id.controlBtn);
        MovieRef = firestore.collection("Movies");
        ControlButton();
        LoadCinema();
        BottomNavigation();
        LoadFilms();
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
        List<String> l = new ArrayList<>();
        l.add("Manh");
        l.add("Ho");
        l.add("Dinh");
        CinemaAutoTv = findViewById(R.id.CinemaFilter);
        CinemaAutoTv.setAdapter(new ArrayAdapter<String>(ReportActivity.this, R.layout.dropdown_item, l));
        CinemaAutoTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color)));
        CinemaAutoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(CinemaAutoTv.getText().toString(),CinemaAutoTv.getText().toString());
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
                    Log.d(String.valueOf(films.size()),String.valueOf(films.size()));
                }
                LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                filmReports.setLayoutManager(VerLayoutManager);
                filmReports.setAdapter(new FilmReportAdapter(films));
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
            }
            return true;
        });
    }
}