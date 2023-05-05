package com.example.movieticketapp.Activity.Account;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    EditText fullNameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = FirebaseRequest.mAuth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);
        ImageView imageView =  findViewById(R.id.addimage);
        fullNameET=findViewById(R.id.fullname);
        emailET=findViewById(R.id.emailaddress);
        passwordET=findViewById(R.id.password);
        confirmPasswordET=findViewById(R.id.confirmpassword);

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
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        Button signUpBt =  findViewById(R.id.SignUpBtn);
        signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if(fullNameET.length()==0)
                {
                    fullNameET.setError("Full Name is not empty!!!");
                    error=true;
                }
                if(emailET.length()==0)
                {
                    emailET.setError("Email is not empty!!!");
                    error=true;
                }
                if(passwordET.length()==0)
                {
                    passwordET.setError("Password is not empty!!!");
                    error=true;
                }
                if(!confirmPasswordET.getText().toString().equals(passwordET.getText().toString()))
                {
                    confirmPasswordET.setError("Password and confirmation passwords are not equals !!!");
                    error=true;
                }
                if(!error){
                    CreateUser(emailET.getText().toString(), passwordET.getText().toString(), fullNameET.getText().toString());
                }
            }
        });
    }
    void CreateUser(String email, String password,String Name )
    {
       FirebaseRequest.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = FirebaseRequest.mAuth.getCurrentUser();
                            UpdateFullName();
                            user.getUid();
                            Users u = new Users(user.getUid(), email, Name);
                            FirebaseRequest.database.collection("Users").document(user.getUid())
                                    .set(u.toJson())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                            Intent i = new Intent(getApplicationContext(), UserProflingActivity.class);
                            startActivity(i);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "The email had used.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
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

                }
            }
        });
    }


}