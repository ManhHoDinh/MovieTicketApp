package com.example.movieticketapp.Activity.Account;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.movieticketapp.Activity.Movie.AddMovieActivity;
import com.example.movieticketapp.Activity.Movie.loadingAlert;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SignUpActivity extends AppCompatActivity {
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

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
    DocumentReference document;
    UploadTask uploadTask;
    EditText fullNameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;
    Uri avataUri = null;
    String avatarUrl;
    loadingAlert loadingDialog;
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
        ImageView imageAvatar = findViewById(R.id.avatarprofile);
        fullNameET=findViewById(R.id.fullname);
        emailET=findViewById(R.id.emailaddress);
        passwordET=findViewById(R.id.password);
        confirmPasswordET=findViewById(R.id.confirmpassword);
        loadingDialog= new loadingAlert(SignUpActivity.this);
        document = databaseReference.collection("Users").document();


        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        imageAvatar.setImageURI(uri);
                        avataUri = uri;

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        Button backBt = findViewById(R.id.backbutton);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayout layoutElement = findViewById(R.id.SignUpLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
                else if(passwordET.length()<=6)
                {
                    passwordET.setError("Password should be at least 6 characters!!!");
                    error=true;
                }
                if(!confirmPasswordET.getText().toString().equals(passwordET.getText().toString()))
                {
                    confirmPasswordET.setError("Password and confirmation passwords are not equals !!!");
                    error=true;
                }
                int totalUploadTasks = 1;
                AtomicInteger completedUploadTasks = new AtomicInteger(0);

                if(!error){
                    loadingDialog.StartAlertDialog();
                    String folder = "Human/"+document.getId(); // Replace "your_folder_name" with the desired folder name
                    String AvatarName = folder + "/avt" ;
                    MediaManager.get().upload(avataUri).option("public_id", AvatarName)
                            .callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {
                                    // your code here
                                }
                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {
                                    // example code starts here
                                    Double progress = (double) bytes/totalBytes;
                                    // post progress to app UI (e.g. progress bar, notification)
                                    // example code ends here
                                }
                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    // your code here
                                    String url = (String) resultData.get("secure_url").toString();
                                    avatarUrl = url;
                                    CreateUser(emailET.getText().toString(), passwordET.getText().toString(), fullNameET.getText().toString(), avatarUrl);
                                }
                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    CreateUser(emailET.getText().toString(), passwordET.getText().toString(), fullNameET.getText().toString(), null);
                                    Toast toast = Toast.makeText(getApplicationContext(),"Have some Errors!!!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    // your code here
                                }})
                            .dispatch();

                }
            }
        });
    }
    void CreateUser(String email, String password,String Name, @Nullable String url)
    {
        String urlAvatar;
        if(url == null){
            urlAvatar = "https://firebasestorage.googleapis.com/v0/b/movie-ticket-app-0.appspot.com/o/avatar.png?alt=media&token=23a1d250-ca27-414b-a46b-bbef69dac7da";

        }
        else urlAvatar = url;
       FirebaseRequest.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = FirebaseRequest.mAuth.getCurrentUser();
                            UpdatePhotho(urlAvatar);
                            user.getUid();
                            Users u = new Users(user.getUid(), Name, email,0, "user", urlAvatar, new ArrayList<>(), new ArrayList<>());
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
                            Users.currentUser = u;
                            Intent i = new Intent(getApplicationContext(), UserProflingActivity.class);
                            startActivity(i);
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

    private void UpdatePhotho( String urlAvatar) {

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(urlAvatar)).setDisplayName(fullNameET.getText().toString())
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