package com.example.movieticketapp.Activity.Cinema;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Adapter.CinameNameAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddCinemaActivity extends AppCompatActivity {
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

    private EditText nameCinema;
    private RoundedImageView imageCinema;

    private AutoCompleteTextView cinemaAddressTv;
    private EditText hotline;
    UploadTask uploadTask;
    private Button confirmBtn;
    private Button cancelBtn;
    private LinearLayout addCinemaLayout;
    private EditText priceCinema;
    private EditText addressCinema;
    boolean isSetImage = false;
    private Button backBtn;

    private final int PICK_IMAGE_REQUEST = 22;
    String cityID ;
    private static String selectedCity;
    private List<String> listCity = new ArrayList<>();
    Uri filePath;
    private String cinemaImg;
    String img;
    City city;
    Cinema cinemaEdit;

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
                            imageCinema.setImageURI(filePath);
                            isSetImage = true;
                            img = UUID.randomUUID().toString();
                            addCinemaLayout.setVisibility(View.GONE);
                            StorageReference ref
                                    = storageReference
                                    .child(img);
                            ref.putFile(filePath).addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            Toast.makeText(AddCinemaActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Toast.makeText(AddCinemaActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);
        initValue();
        updateImage();
        Intent intent = getIntent();
        cityID = intent.getStringExtra("cityID");
        cinemaEdit = intent.getParcelableExtra("cinema");
        backBtn = findViewById(R.id.backbutton);
        LinearLayoutCompat layoutElement = findViewById(R.id.LinerLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCinemaInfo();
            }
        });
        if(cinemaEdit != null){
            nameCinema.setText(cinemaEdit.getName());
            Picasso.get().load(cinemaEdit.getImage()).into(imageCinema);
            isSetImage = true;
            hotline.setText(cinemaEdit.getHotline());
            priceCinema.setText(String.valueOf(cinemaEdit.getPrice()));
            addressCinema.setText(cinemaEdit.getAddress());
            img = cinemaEdit.getImage();
            addCinemaLayout.setVisibility(View.GONE);
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void updateImage(){
        addCinemaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityLauch.launch(imageIntent);
            }
        });
        imageCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityLauch.launch(imageIntent);
            }
        });
    }

    void initValue(){
        nameCinema = findViewById(R.id.cinemaNameEt);
        imageCinema = findViewById(R.id.cinemaImage);
        hotline = findViewById(R.id.hotLineEt);
        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        addCinemaLayout = findViewById(R.id.addCinemaLayout);
        priceCinema = findViewById(R.id.priceEt);
        addressCinema = findViewById(R.id.addressEt);
    }

    void createCinemaInfo(){
        String name = nameCinema.getText().toString();
        String phoneNo = hotline.getText().toString();
        String price = priceCinema.getText().toString();
        String address = addressCinema.getText().toString();
        if(name.equals("")||phoneNo.equals("")||price.equals("") || address.equals("") || isSetImage == false ){
            Toast.makeText(this, "Please type full information!", Toast.LENGTH_SHORT).show();
        }
        else {
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
                            CollectionReference cinemaCollection = FirebaseFirestore.getInstance().collection("Cinema");
                            DocumentReference doc;
                            if(cinemaEdit == null){
                                doc =cinemaCollection.document();
                            }
                            else doc = cinemaCollection.document(cinemaEdit.getCinemaID());
                            Cinema cinema = new Cinema(doc.getId(),cityID, name, address, Integer.parseInt(price), phoneNo, cinemaImg);
                            doc.set(cinema.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                CollectionReference cinemaCollection = FirebaseFirestore.getInstance().collection("Cinema");
                DocumentReference doc;
                if(cinemaEdit == null){
                    doc =cinemaCollection.document();
                }
                else doc = cinemaCollection.document(cinemaEdit.getCinemaID());
                Cinema cinema = new Cinema(doc.getId(),cityID, name, address, Integer.parseInt(price), phoneNo, img);
                doc.set(cinema.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });

            }
        }
    }
}