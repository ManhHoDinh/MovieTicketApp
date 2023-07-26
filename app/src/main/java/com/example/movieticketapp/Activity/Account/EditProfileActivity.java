package com.example.movieticketapp.Activity.Account;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    EditText fullNameET;
    EditText emailET;

    ImageView addImage;
    RoundedImageView avatarImg;
    UploadTask uploadTask;
    String cinemaImg;
    Uri filePath;
    String img;
    FirebaseUser currentUser =  FirebaseRequest.mAuth.getCurrentUser();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
    ActivityResultLauncher<Intent> activityLauch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if(data.getData() != null){
                            filePath = data.getData();

                            avatarImg.setImageURI(filePath);
                            img = UUID.randomUUID().toString();

                            StorageReference ref
                                    = storageReference
                                    .child(img);
                            ref.putFile(filePath).addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            Toast.makeText(EditProfileActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(EditProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
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
        addImage =  findViewById(R.id.addimage);
        avatarImg = findViewById(R.id.avatarImg);
        fullNameET=findViewById(R.id.fullname);
        emailET=findViewById(R.id.emailaddress);

        FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users user = documentSnapshot.toObject(Users.class);
                fullNameET.setText(user.getName());
                emailET.setText(user.getEmail());
                Picasso.get().load(user.getAvatar()).into(avatarImg);
            }
        });
//        fullNameET.setText(FirebaseRequest.mAuth.getCurrentUser().getDisplayName());
//        emailET.setText(FirebaseRequest.mAuth.getCurrentUser().getEmail());
//        Picasso.get().load(currentUser.getPhotoUrl()).into(avatarImg);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityLauch.launch(intent);
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
        else{
            Update();
            finish();
        }
        updateAvatar();
    }
    void Update()
    {
        if(!emailET.getText().toString().equals(FirebaseRequest.mAuth.getCurrentUser().getEmail()))
            UpdateEmail();
        if(!fullNameET.getText().toString().equals(FirebaseRequest.mAuth.getCurrentUser().getDisplayName()))
            UpdateFullName();
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
        FirebaseRequest.database.collection("Users").document(currentUser.getUid()).update("Name", fullNameET.getText().toString());

    }
    void UpdateEmail()
    {
        FirebaseRequest.mAuth.getCurrentUser().updateEmail(emailET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseRequest.mAuth.signOut();
                    Intent loginIntent = new Intent(EditProfileActivity.this, SignInActivity.class);
                    TaskStackBuilder.create(EditProfileActivity.this).addNextIntentWithParentStack(loginIntent).startActivities();
                } else {
                    UpdateError("Email");
                }
            }
        });
        FirebaseRequest.database.collection("Users").document(currentUser.getUid()).update("Email", emailET.getText().toString());
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
    void updateAvatar(){
        if(filePath != null){
            StorageReference ref = storageReference.child(img);
            uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        cinemaImg = task.getResult().toString();
                        CollectionReference cinemaCollection = FirebaseFirestore.getInstance().collection("Users");
                        DocumentReference doc;
                        doc = cinemaCollection.document(currentUser.getUid());

                        doc.update("avatar", cinemaImg);


                    }
                }
            });
        }

    }
}