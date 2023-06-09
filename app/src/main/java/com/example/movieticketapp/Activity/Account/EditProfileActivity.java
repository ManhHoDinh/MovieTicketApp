package com.example.movieticketapp.Activity.Account;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditProfileActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    EditText fullNameET;
    EditText emailET;
    EditText passwordET;
    EditText CurrentPasswordET;
    EditText confirmPasswordET;
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
        setContentView(R.layout.edit_profile_screen);
        ImageView imageView =  findViewById(R.id.addimage);
        fullNameET=findViewById(R.id.fullname);
        emailET=findViewById(R.id.emailaddress);
        passwordET=findViewById(R.id.password);
        confirmPasswordET=findViewById(R.id.confirmpassword);
        CurrentPasswordET=findViewById(R.id.currentPassword);
        fullNameET.setText(FirebaseRequest.mAuth.getCurrentUser().getDisplayName());
        emailET.setText(FirebaseRequest.mAuth.getCurrentUser().getEmail());
        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
        });
        Button backBt = findViewById(R.id.backbutton);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button UpdateBtn =  findViewById(R.id.UpdateBtn);
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });

    }
    void UpdateUser()
    {
        if(fullNameET.length()==0)
        {
            fullNameET.setError("Full Name is not empty!!!");
        }
        else if(emailET.length()==0)
        {
            emailET.setError("Email is not empty!!!");
        }
        else if(CurrentPasswordET.length()==0)
        {
            CurrentPasswordET.setError("Current Password is not empty!!!");
        }
        else if(passwordET.length()==0)
        {
            passwordET.setError("Password is not empty!!!");
        }
        else if(!confirmPasswordET.getText().toString().equals(passwordET.getText().toString()))
        {
            confirmPasswordET.setError("Password and confirmation passwords are not equals !!!");
        }
        else{
            AuthCredential credential = EmailAuthProvider.getCredential(FirebaseRequest.mAuth.getCurrentUser().getEmail(), CurrentPasswordET.getText().toString());
            FirebaseRequest.mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // User has been successfully re-authenticated
                        // You can update the password now
                        Update();
                    } else {
                        // An error occurred while re-authenticating the user
                        // Handle the error
                        UpdateError("Update");
                    }
                }
            });

        }
    }
    void Update()
    {
        String name = "new_password";
        if(!CurrentPasswordET.getText().toString().equals(passwordET.getText().toString()))
            UpdatePassword();
        if(!emailET.getText().toString().equals(FirebaseRequest.mAuth.getCurrentUser().getEmail()))
            UpdateEmail();
        if(!fullNameET.getText().toString().equals(FirebaseRequest.mAuth.getCurrentUser().getDisplayName()))
            UpdateFullName();
    }
    void UpdatePassword()
    {
        String newPassword = "new_password";
        FirebaseRequest.mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    // An error occurred while updating the user password
                    // Handle the error
                    UpdateError("Password");
                }
            }
        });
    }
    void UpdateFullName()
    {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullNameET.getText().toString())
                .build();
        FirebaseRequest.mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    // An error occurred while updating the user password
                    // Handle the error
                    UpdateError("Full Name");
                }
            }
        });
    }
    void UpdateEmail()
    {
        FirebaseRequest.mAuth.getCurrentUser().updateEmail(emailET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                    UpdateError("Email");
                }
            }
        });
    }
//    void SuccessUpdate()
//    {
//        Intent i = new Intent(EditProfileActivity.this, AccountActivity.class);
//        startActivity(i);
//    }
    void UpdateError(String error)
    {
        Toast.makeText(EditProfileActivity.this, "Edit Profile failed : " + error,
                Toast.LENGTH_SHORT).show();
    }
}