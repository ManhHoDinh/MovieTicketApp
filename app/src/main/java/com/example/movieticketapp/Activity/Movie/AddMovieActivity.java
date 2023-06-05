package com.example.movieticketapp.Activity.Movie;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMovieActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    int th;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference storageReference2 = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
    DocumentReference document;
    ImageView moviebackground;
    TextView textbg;
    ImageView imbg;

    RoundedImageView movieavatar;
    TextView textavt;
    ImageView imavt;
    EditText description;
    EditText movieName;
    EditText movieKind;
    EditText movieDurarion;
    EditText movieStatus;
    RoundedImageView movieactor;
    ImageView imcast;
    TextView textcast;

    VideoView movietrailer;
    ImageView imtrailer;
    TextView texttrailer;
    Button applyButton;
    Button cancleButton;
    Uri backgrounduri;
    Uri avataruri = null;
    Uri actoruri = null;
    Uri traileruri = null;

    String urlbackground;

    String urlavatar;
    String urlactor;
    String urltrailer;
    UploadTask uploadTask;
    UploadTask uploadTask2;
    boolean error = false;
    boolean finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_screen);
        TextView cas = (TextView) findViewById(R.id.castCrewTV);
        cas.setText("Cast & Crew");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePage:
                    startActivity(new Intent(this, HomeActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.walletPage:
                    startActivity(new Intent(this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

        moviebackground = (ImageView) findViewById(R.id.moviebackground);
        textbg = (TextView) findViewById(R.id.textbackground);
        imbg = (ImageView) findViewById(R.id.imbackground);

        movieavatar = (RoundedImageView) findViewById(R.id.movieavatar);
        textavt = (TextView) findViewById(R.id.textavt);
        imavt = (ImageView) findViewById(R.id.imavt);

        description = (EditText) findViewById(R.id.moviedes);
        movieName = (EditText) findViewById(R.id.movieName);
        movieKind = (EditText) findViewById(R.id.movieKind);
        movieDurarion =(EditText) findViewById(R.id.movieDuration);
        movieStatus = (EditText) findViewById(R.id.moviestatus);
        applyButton = (Button) findViewById(R.id.applybutton);
        cancleButton = (Button) findViewById(R.id.cancelbutton);

        movieactor = (RoundedImageView) findViewById(R.id.movieactor);
        imcast = (ImageView) findViewById(R.id.imcast);
        textcast = (TextView) findViewById(R.id.textcast);

        movietrailer = (VideoView) findViewById(R.id.movietrailer);
        imtrailer = (ImageView) findViewById(R.id.imtrailer);
        texttrailer = (TextView) findViewById(R.id.texttrailer);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        switch (th) {
                            case 0:
                                moviebackground.setImageURI(uri);
                                backgrounduri = uri;
                                break;
                            case 1:
                                movieavatar.setImageURI(uri);
                                avataruri = uri;
                                break;
                            case 2:
                                movieactor.setImageURI(uri);
                                actoruri = uri;
                                break;
                            case 3:
                                movietrailer.setVideoURI(uri);
                                traileruri = uri;
                                break;
                        }

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                }
            );


        moviebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                th = 0;
                textbg.setText("");
                imbg.setImageResource(0);
                 
            }
        });
        movieavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                th = 1;
                textavt.setText("");
                imavt.setImageResource(0);
                 
            }
        });
        movieactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                th = 2;
                textcast.setText("");
                imcast.setImageResource(0);
            }
        });
        movietrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                        .build());
                th = 3;
                texttrailer.setText("");
                imtrailer.setImageResource(0);
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieName.length() == 0) {
                    movieName.setError("Movie Name cannot be empty!!!");
                    error = true;
                }
                if (movieKind.length() == 0) {
                    movieKind.setError("Movie Kind cannot be empty!!!");
                    error = true;
                }
                if (movieDurarion.length() == 0) {
                    movieDurarion.setError("Movie Duration cannot be empty!!!");
                    error = true;
                }
                if (movieStatus.length() == 0) {
                    movieStatus.setError("Movie Status cannot be empty!!!");
                    error = true;
                }

                if (!error)
                {
                    Calendar calFordData = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    String saveCurrentData = currentDate.format(calFordData.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    String saveCurrentTime = currentTime.format(calFordData.getTime());

                    String postRandomName = saveCurrentData + saveCurrentTime;

                    storageReference = storageReference.child(postRandomName+".jpg");
                    uploadTask = storageReference.putFile(backgrounduri);
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
                                urlbackground = task.getResult().toString();
                                SaveDatatoDatabase();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    storageReference2 = storageReference2.child(postRandomName+"th2.jpg");
                    uploadTask2 = storageReference2.putFile(avataruri);
                    Task<Uri> urlTask2 = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storageReference2.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                urlavatar = task.getResult().toString();
                                SaveDatatoDatabase();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Have some errors!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void SaveDatatoDatabase() {
        document = databaseReference.document("Movies/"+movieName.getText().toString());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("BackGroundImage", urlbackground);
        data.put("PosterImage", urlbackground);
        data.put("PrimaryImage", urlavatar);
        data.put("description", description.getText().toString());
        data.put("durationTime", movieDurarion.getText().toString());
        data.put("genre", movieKind.getText().toString());
        data.put("id", movieName.getText().toString());
        data.put("name", movieName.getText().toString());
        data.put("status", movieStatus.getText().toString());
        data.put("vote", "0");
        document.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Add Movie Success!", Toast.LENGTH_SHORT).show();

            }
        });
    }
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
}