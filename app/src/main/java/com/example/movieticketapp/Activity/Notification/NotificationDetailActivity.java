package com.example.movieticketapp.Activity.Notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Report.ReportActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.NotificationModel;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationDetailActivity extends AppCompatActivity {
    ImageView backBtn;
    private BottomNavigationView bottomNavigationView;
    TextView title;
    TextView description;
    NotificationModel notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        BottomNavigation();
        backBtn = findViewById(R.id.btnBack);
        title = findViewById(R.id.titleContent);
        description = findViewById(R.id.descriptionContent);
        Intent intent = getIntent();
        notification = intent.getParcelableExtra(ExtraIntent.notification);

        if(notification!=null)
        {
            title.setText(notification.getHeading());
            description.setText(notification.getDescription());
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void BottomNavigation()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().findItem(R.id.NotificationPage).setChecked(true);
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
                    startActivity(new Intent(NotificationDetailActivity.this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(NotificationDetailActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.walletPage:
                    startActivity(new Intent(NotificationDetailActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ReportPage:
                    startActivity(new Intent(NotificationDetailActivity.this, ReportActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });
    }
}