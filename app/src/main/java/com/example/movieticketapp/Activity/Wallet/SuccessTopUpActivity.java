package com.example.movieticketapp.Activity.Wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.PriceGridAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class SuccessTopUpActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
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
    }

    private Button backWalletBtn;
    private TextView backHomeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_top_up);
        backWalletBtn = (Button) findViewById(R.id.backWalletBtn);
        backHomeTv = (TextView) findViewById(R.id.backHomeTv);
        Intent intent = getIntent();
        DocumentReference docRef = FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        InforBooked.getInstance().total += Integer.parseInt(String.valueOf(document.get("wallet")));



                     int wallet =Integer.parseInt(String.valueOf(document.get("Wallet")))+ Integer.parseInt(intent.getStringExtra("selectedPrice"));
                        docRef.update("Wallet", wallet);

                    } else {
                        Log.e("c", "No such document");
                    }
                } else {
                    Log.e("dđ", "get failed with ", task.getException());
                }
            }
        });
        //int wallet = FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get("wallet"). + Integer.parseInt(intent.getStringExtra("selectedPrice"));
     //   FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).update("wallet", wallet);
       // InforBooked.getInstance().total+= Integer.parseInt(intent.getStringExtra("selectedPrice"));
        backWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessTopUpActivity.this, MyWalletActivity.class));
            }
        });
        backHomeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessTopUpActivity.this, HomeActivity.class));
            }
        });
    }
}