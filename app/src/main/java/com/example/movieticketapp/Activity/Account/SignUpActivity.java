package com.example.movieticketapp.Activity.Account;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Users;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

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
    String fullname;
    CheckBox checkad;
    EditText adminET;
    boolean isAd = false;
    boolean error = false;
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
        checkad = (CheckBox)findViewById(R.id.checkadmin);
        adminET = (EditText) findViewById(R.id.admincode);
        adminET.setVisibility(View.INVISIBLE);

        checkad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    adminET.setVisibility(View.VISIBLE);
                }
                else
                {
                    adminET.setVisibility(View.INVISIBLE);
                }
            }
        });

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
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        Button signUpBt =  findViewById(R.id.SignUpBtn);
        signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if(checkad.isChecked() == true)
                {
                    if(adminET.length()==0)
                    {
                        adminET.setError("Admin code cannot be empty!!!");
                        error= true;
                    }
                    else
                    {
                        String adcode = adminET.getText().toString();
                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                        DocumentReference userRef = firebaseFirestore.collection("IdForAdmin").document(adcode);
                        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        isAd = true;
                                    } else {
                                        isAd = false;
                                        error = true;
                                        Toast.makeText(getApplicationContext(), "This code is not exists!!!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Lỗi khi truy vấn Firestore", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                if(!error){
                    fullname = fullNameET.getText().toString();
                    Calendar calFordData = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    String saveCurrentData = currentDate.format(calFordData.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    String saveCurrentTime = currentTime.format(calFordData.getTime());

                    String postRandomName = saveCurrentData + saveCurrentTime;

                    storageReference = storageReference.child(postRandomName+"as.jpg");
                    uploadTask = storageReference.putFile(avataUri);
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                avatarUrl = task.getResult().toString();
                                CreateUser(emailET.getText().toString(), passwordET.getText().toString(), fullNameET.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
                            UpdatePhotho();
                            user.getUid();
                            String type;
                            if (isAd)
                            {
                                type = "admin";
                            }
                            else
                            {
                                type = "user";
                            }
                            Users u = new Users(user.getUid(), Name, email,0, type, avatarUrl);
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

    private void UpdatePhotho() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(avatarUrl)).setDisplayName(fullname)
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