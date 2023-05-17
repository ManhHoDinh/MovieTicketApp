package com.example.movieticketapp.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {
    LinearLayoutCompat EditProfile;
    LinearLayoutCompat MyWallet;
    LinearLayoutCompat HelpCentre;
    LinearLayoutCompat SignOut;
    TextView Name;
    TextView Email;
    ImageView Avatar;
    ImageView BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_account_screen);
        EditProfile=findViewById(R.id.EditProfile);
        MyWallet=findViewById(R.id.MyWallet);
        HelpCentre=findViewById(R.id.HelpCentre);
        SignOut=findViewById(R.id.SignOut);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        BackBtn=findViewById(R.id.Back);
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        Name.setText(currentUser.getDisplayName());
        Email.setText(currentUser.getEmail());
        Avatar= findViewById(R.id.avatar);
        if (currentUser.getPhotoUrl()!=null)
        Picasso.get().load(currentUser.getPhotoUrl()).into(Avatar);
        else Avatar.setImageResource(R.drawable.avatar);
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
        HelpCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(AccountActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }
}