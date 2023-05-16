package com.example.movieticketapp.Activity.Account;

import static com.example.movieticketapp.Firebase.FirebaseRequest.mAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ea.async.Async;
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.splash_logo);
        FirebaseRequest.mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            getUser(mAuth.getCurrentUser().getUid());
        }
        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method
            @Override

            public void run() {
                Intent i;
                SharedPreferences sharedPref = getSharedPreferences("shared_prefs",0);
                boolean IsUsed = sharedPref.getBoolean(String.valueOf(R.string.CheckUsed),false);
                if(IsUsed) {
                    if (mAuth.getCurrentUser() != null) {

                        i = new Intent(SplashActivity.this, HomeActivity.class);
                    }
                    else
                        i = new Intent(SplashActivity.this, SignInActivity.class);
                }
                else
                    i = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(i);
                finish();
            }

        }, 5*1000); // wait for 5 seconds
    }

    Integer getUser(String id)
    {
        {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.collection("Users").document(id);
            Log.d("ID",id);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) { if (error != null) {
                    return;
                }
                    if (snapshot != null && snapshot.exists()) {
                        Users.currentUser = snapshot.toObject(Users.class);
                        Log.d("Email",Users.currentUser.getEmail());
                    }
                }
            });
            return 0;
        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return  0;
//        }
    }

}