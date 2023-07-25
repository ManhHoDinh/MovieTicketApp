package com.example.movieticketapp.Activity.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

public class ChangePasswordActivity extends AppCompatActivity {
    Button backBtn;
    EditText passwordET;
    EditText CurrentPasswordET;
    EditText confirmPasswordET;

    Button changePasswordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        backBtn = findViewById(R.id.backbutton);
        passwordET=findViewById(R.id.password);
        confirmPasswordET=findViewById(R.id.confirmpassword);
        CurrentPasswordET=findViewById(R.id.currentPassword);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }
    void changePassword(){
        if(CurrentPasswordET.length()==0)
        {
            CurrentPasswordET.setError("Current Password is not empty!!!");
        }
        else if(passwordET.length()==0)
        {
            passwordET.setError("Password is not empty!!!");
        }
        else if(passwordET.length() < 6){
            passwordET.setError("Password should be at least 6 characters!!!");
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
                        Toast.makeText(ChangePasswordActivity.this, "Current password is not correct!", Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }
    }
    void Update()
    {
        String name = "new_password";
        if(!CurrentPasswordET.getText().toString().equals(passwordET.getText().toString()))
            UpdatePassword(passwordET.getText().toString());

    }
    void UpdatePassword(String newPassword)
    {
        FirebaseRequest.mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseRequest.mAuth.signOut();
                    Intent loginIntent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                    TaskStackBuilder.create(ChangePasswordActivity.this).addNextIntentWithParentStack(loginIntent).startActivities();
                } else {
                    // An error occurred while updating the user password
                    // Handle the error
                }
            }
        });
    }
    void UpdateError(String error)
    {
        Toast.makeText(ChangePasswordActivity.this, "Edit Profile failed : " + error,
                Toast.LENGTH_SHORT).show();
    }

}