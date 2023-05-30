package com.example.movieticketapp.Activity.Report;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private  BottomNavigationView bottomNavigationView;
    private AutoCompleteTextView CinemaAutoTv;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_screen);
        firestore = FirebaseFirestore.getInstance();
       // Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        LoadCinema();
        BottomNavigation();
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