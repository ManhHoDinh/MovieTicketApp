package com.example.movieticketapp.Activity.Account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    LinearLayoutCompat EditProfile;
    LinearLayoutCompat MyWallet;
    LinearLayoutCompat changePassword;
    LinearLayoutCompat SignOut;
    TextView Name;
    TextView Email;
    ImageView Avatar;
    ImageView BackBtn;
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
        setContentView(R.layout.info_account_screen);
        EditProfile=findViewById(R.id.EditProfile);
        MyWallet=findViewById(R.id.MyWallet);
        changePassword=findViewById(R.id.changePassword);
        SignOut=findViewById(R.id.SignOut);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        BackBtn=findViewById(R.id.Back);
        if(Users.currentUser.getAccountType().equals("admin")){
            MyWallet.setVisibility(View.GONE);
        }
        else  MyWallet.setVisibility(View.VISIBLE);
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseRequest.database.collection("Users").document(currentUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Users user = value.toObject(Users.class);
                Name.setText(user.getName());
                Email.setText(user.getEmail());
                Avatar= findViewById(R.id.avatar);
                Picasso.get().load(user.getAvatar()).into(Avatar);

            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
        MyWallet.setOnClickListener(view -> {
            Intent i = new Intent(AccountActivity.this, MyWalletActivity.class);
            startActivity(i);
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(i);

            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(AccountActivity.this, SignInActivity.class);
                TaskStackBuilder.create(AccountActivity.this).addNextIntentWithParentStack(loginIntent).startActivities();
            }
        });
    }
}