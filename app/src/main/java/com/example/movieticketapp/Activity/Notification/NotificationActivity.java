package com.example.movieticketapp.Activity.Notification;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Movie.ViewAllActivity;
import com.example.movieticketapp.Activity.Report.ReportActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.NotificationAdapter;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.NotificationModel;
import com.example.movieticketapp.Model.UserAndDiscount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private  BottomNavigationView bottomNavigationView;
    TextInputEditText Description;
    TextInputEditText Heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_screen);
        BottomNavigation();
        LoadNotification();
        AddNotification();
        checkAccountType();
    }
    void AddNotification(){
        Description= findViewById(R.id.DescriptionET);
        Heading=findViewById(R.id.HeadingET);
        AppCompatButton SaveBtn = findViewById(R.id.SaveBtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if(Description.length()==0)
                {
                    Description.setError("Full Name is not empty!!!");
                    error=true;
                }
                if(Heading.length()==0)
                {
                    Heading.setError("Email is not empty!!!");
                    error=true;
                }
                if(!error)
                {
                    AddNotificationToFirebase();
                }

            }

            private void AddNotificationToFirebase() {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference doc = db.collection(NotificationModel.CollectionName).document();;

                FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

                NotificationModel notification = new NotificationModel(doc.getId(), Heading.getText().toString(),  Description.getText().toString(),currentUser.getDisplayName(), Timestamp.now());
                doc.set(notification.toJson())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Heading.setText("");
                                Description.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });
    }
    private void LoadNotification() {
        RecyclerView NotificationRV=findViewById(R.id.Notifications);
        List<NotificationModel> Notifications = new ArrayList<NotificationModel>();
        FirebaseFirestore.getInstance().collection(NotificationModel.CollectionName).orderBy("PostTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Notifications.clear();
                for(DocumentSnapshot doc : value){
                    NotificationModel n = doc.toObject(NotificationModel.class);
                    Notifications.add(n);
                }
                Log.d(String.valueOf(Notifications.size()),String.valueOf(Notifications.size()));
                NotificationAdapter notificationAdapter = new NotificationAdapter(Notifications);
                NotificationRV.setAdapter(notificationAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
                NotificationRV.setLayoutManager(layoutManager);
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
                    startActivity(new Intent(NotificationActivity.this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(NotificationActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.walletPage:
                    startActivity(new Intent(NotificationActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ReportPage:
                    startActivity(new Intent(NotificationActivity.this, ReportActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });
    }
    void checkAccountType() {
        try {
            Log.d("account type", Users.currentUser.getAccountType());
            if (Users.currentUser != null)
                if ((!(Users.currentUser.getAccountType().toString()).equals("admin"))) {
                    HideAddNotificationLayout();
            }

        } catch (Exception e) {
            HideAddNotificationLayout();
        }

    }
    void HideAddNotificationLayout()
    {
        LinearLayoutCompat AddNotificationLayout= findViewById(R.id.AddNotificationLayout);
        ViewGroup.LayoutParams serviceParams = AddNotificationLayout.getLayoutParams();
        serviceParams.height = 0;
        AddNotificationLayout.setLayoutParams(serviceParams);
        AddNotificationLayout.setVisibility(View.INVISIBLE);
        TextView NotificationTitle = findViewById(R.id.NotificationTitle);
        NotificationTitle.setText("From Manager");
    }

}