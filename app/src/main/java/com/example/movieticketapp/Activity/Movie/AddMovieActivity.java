package com.example.movieticketapp.Activity.Movie;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.Any;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddMovieActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    int th;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
    DocumentReference document;
    EditText description;
    EditText movieName;
    EditText movieKind;
    EditText movieDurarion;
    EditText movieStatus;
    Button applyButton;
    Button cancleButton;
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

        ImageView moviebackground = (ImageView) findViewById(R.id.moviebackground);
        TextView textbg = (TextView) findViewById(R.id.textbackground);
        ImageView imbg = (ImageView) findViewById(R.id.imbackground);

        RoundedImageView movieavatar = (RoundedImageView) findViewById(R.id.movieavatar);
        TextView textavt = (TextView) findViewById(R.id.textavt);
        ImageView imavt = (ImageView) findViewById(R.id.imavt);

        description = (EditText) findViewById(R.id.moviedes);
        movieName = (EditText) findViewById(R.id.movieName);
        movieKind = (EditText) findViewById(R.id.movieKind);
        movieDurarion =(EditText) findViewById(R.id.movieDuration);
        movieStatus = (EditText) findViewById(R.id.moviestatus);
        applyButton = (Button) findViewById(R.id.applybutton);

        RoundedImageView movieactor = (RoundedImageView) findViewById(R.id.movieactor);
        ImageView imcast = (ImageView) findViewById(R.id.imcast);
        TextView textcast = (TextView) findViewById(R.id.textcast);

        VideoView movietrailer = (VideoView) findViewById(R.id.movietrailer);
        ImageView imtrailer = (ImageView) findViewById(R.id.imtrailer);
        TextView texttrailer = (TextView) findViewById(R.id.texttrailer);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        switch (th) {
                            case 0:
                                moviebackground.setImageURI(uri);
                                break;
                            case 1:
                                movieavatar.setImageURI(uri);
                                break;
                            case 2:
                                movieactor.setImageURI(uri);
                                break;
                            case 3:
                                movietrailer.setVideoURI(uri);
                                break;
                        }

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
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
                document = databaseReference.document("Movies/PhamThanhTuong");
                HashMap<String, Any> data = new HashMap<String, Any>();
                data["BackGroudImage"] = movieName.getText().toString();
                document.set("PosterImage");
                document.set("PrimaryImage");
                document.set("description");
                document.set("durationTime");
                document.set("genre");
                document.set("id");
                document.set("name");
                document.set("status");
                document.set("vote");
            }
        });
    }
}