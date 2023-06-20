package com.example.movieticketapp.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ConfirmationProfileActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_profile_screen);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ImageView avatar = (ImageView) findViewById(R.id.profilePic);
        if (currentUser!= null)
        {
            if (currentUser.getPhotoUrl() != null)
                Picasso.get().load(currentUser.getPhotoUrl()).into(avatar);
            else avatar.setImageResource(R.drawable.avatar);
        }
        TextView name = (TextView) findViewById(R.id.txtExampleName);
        if (currentUser!= null)
        {
            if (currentUser.getDisplayName()!=null)
                name.setText(currentUser.getDisplayName());
        }
        Button btnCreateAccount= findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
        ImageView btnBack= findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}