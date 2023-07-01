package com.example.movieticketapp.Activity.Movie;

import static androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.EditTrailerAdapter;
import com.example.movieticketapp.Adapter.ServiceAdapter;
import com.example.movieticketapp.Adapter.TrailerMovieApdapter;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Value;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class AddMovieActivity extends AppCompatActivity{
    public static List<Uri> videoUris= new ArrayList<>();
    public static  Uri defaultUri;
    public  ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    public  ActivityResultLauncher<PickVisualMediaRequest> pickVideo;
    int th;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference storageReference2 = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
    DocumentReference document;
    ImageView moviebackground;
    TextView textbg;
    ImageView imbg;

    ImageView movieavatar;
    TextView textavt;
    ImageView imavt;
    EditText description;
    EditText movieName;
    TextView movieKind;
    EditText movieDurarion;
    public static Context context;
    Button applyButton;
    Button cancleButton;
    Uri backgrounduri;
    Uri avataruri = null;

    String urlbackground;
    Timestamp dateStart;
    String urlavatar;
    UploadTask uploadTask;
    UploadTask uploadTask2;
    Button calendarButton;
    TrailerMovieApdapter adapter;
    List<String> InStorageVideoUris=new ArrayList<>();
    loadingAlert loadingDialog;
    public static String defaultAddTrailer = "Add";

    public static List<String> videos=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_screen);
        InStorageVideoUris.clear();
        loadingDialog= new loadingAlert(AddMovieActivity.this);
        defaultUri=Uri.parse("https://example.com/default");;
        calendarButton = findViewById(R.id.Calendar);
        document = databaseReference.collection("Movies").document();

       calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show calendar dialog
                showCalendarDialog();
                dismissKeyboard(v);
            }
        });
        moviebackground = (ImageView) findViewById(R.id.moviebackground);
        textbg = (TextView) findViewById(R.id.textbackground);
        imbg = (ImageView) findViewById(R.id.imbackground);

        movieavatar =  findViewById(R.id.movieavatar);
        textavt = (TextView) findViewById(R.id.textavt);
        imavt = (ImageView) findViewById(R.id.imavt);

        description = (EditText) findViewById(R.id.MovieDescription);
        movieName = (EditText) findViewById(R.id.movieName);
        movieKind = (TextView) findViewById(R.id.movieKind);
        movieDurarion =(EditText) findViewById(R.id.movieDuration);
        applyButton = (Button) findViewById(R.id.applybutton);
        cancleButton = (Button) findViewById(R.id.cancelbutton);

        LinearLayout layoutElement = findViewById(R.id.AddMovieLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                dismissKeyboard(v);

            }
        });
        movieKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder= onCreateDialog();
                builder.show();
                dismissKeyboard(view);
            }
        });
        RecyclerView containerLayout = findViewById(R.id.containerLayout);
        Button addButton = findViewById(R.id.addButton);
        adapter = new TrailerMovieApdapter(AddMovieActivity.this);
        containerLayout.setAdapter(adapter);
        LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        containerLayout.setLayoutManager(VerLayoutManager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videos.size()==0)
                {
                    InStorageVideoUris.clear();
                    AddMovieActivity.videoUris.clear();
                }
                videos.add(defaultAddTrailer);
                videoUris.add(defaultUri);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Add Trailer Layout", Toast.LENGTH_SHORT).show();
            }
        });



        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                            if (uri != null) {
                                switch (th) {
                                    case 0:
                                        moviebackground.setImageURI(uri);
                                        backgrounduri = uri;
                                        if(uri!=null)
                                        {
                                            textbg.setText("");
                                            imbg.setImageResource(0);
                                        }
                                        break;
                                    case 1:
                                        movieavatar.setImageURI(uri);
                                        avataruri = uri;
                                        if(uri!=null)
                                        {
                                            textavt.setText("");
                                            imavt.setImageResource(0);
                                        }
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
                dismissKeyboard(view);
            }
        });
        movieavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                th = 1;
                dismissKeyboard(view);
            }
        });

       pickVideo =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        int position = adapter.getSelectedPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            adapter.updateVideoElement(uri, position);
                        }
                    } else {
                        Log.d("VideoPicker", "No video selected");
                    }
                });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                boolean error = false;
                int totalUploadTasks = 2 + AddMovieActivity.videoUris.size();
                AtomicInteger completedUploadTasks = new AtomicInteger(0);
                if (movieName.length() == 0) {
                    movieName.setError("Movie Name cannot be empty!!!");
                    error = true;
                }
                if (backgrounduri == null) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Chose movie background, please!!!", Toast.LENGTH_SHORT);
                    toast.show();
                    error = true;
                }
                if (avataruri==null) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Chose movie avatar, please!!!", Toast.LENGTH_SHORT);
                    toast.show();
                    error = true;
                }
                if (movieDurarion.length() == 0) {
                    movieDurarion.setError("Movie Duration cannot be empty!!!");
                    error = true;
                }
                if(dateStart==null)
                {
                    error=true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Chose Start Date, Please!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (!error)
                {
                    loadingDialog.StartAlertDialog();

                    String MovieName = movieName.getText().toString();
                    storageReference = storageReference.child("Movies/"+MovieName+"/"+MovieName+"Poster.jpg");
                    uploadTask = storageReference.putFile(backgrounduri);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                                Toast.makeText(getApplicationContext(), "ERROR BACKGROUND UPLOAD!!!", Toast.LENGTH_SHORT).show();
                                loadingDialog.closeLoadingAlert();
                            }
                            if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                RefeshScreen();
                                finish();
                                Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                           }
                    });
                    storageReference2 = storageReference2.child("Movies/"+MovieName+"/"+MovieName+"Primary.jpg");
                    uploadTask2 = storageReference2.putFile(avataruri);
                    uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                                Toast.makeText(getApplicationContext(), "ERROR AVATAR UPLOAD!!!", Toast.LENGTH_SHORT).show();
                                loadingDialog.closeLoadingAlert();
                            }
                            if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                               RefeshScreen();
                               finish();
                                Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                     for(int i = 0; i < AddMovieActivity.videoUris.size();i++)
                    {
                        StorageReference VideoStorageReference= FirebaseStorage.getInstance().getReference().child("Movies/"+MovieName+"/"+MovieName+"Video"+String.valueOf(i)+".mp4");
                        completedUploadTasks.incrementAndGet();
                        if(AddMovieActivity.videoUris.get(i)== AddMovieActivity.defaultUri)
                        {
                            if(i==AddMovieActivity.videoUris.size()-1&& uploadTask.isComplete()&&uploadTask2.isComplete())
                            {
                                RefeshScreen();
                                finish();
                                Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                                continue;
                        }

                        VideoStorageReference.putFile(AddMovieActivity.videoUris.get(i)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return VideoStorageReference.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    InStorageVideoUris.add(task.getResult().toString());
                                    SaveDatatoDatabase();
                                } else {
                                    Toast.makeText(getApplicationContext(), "ERROR VIDEO UPLOAD!!!", Toast.LENGTH_SHORT).show();
                                    loadingDialog.closeLoadingAlert();
                                }
                                if (completedUploadTasks.get() == totalUploadTasks) {
                                  RefeshScreen();
                                  finish();
                                  Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                  toast.show();
                                }
                            }
                        });
                    }
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
                dismissKeyboard(view);
                RefeshScreen();
                finish();
            }
        });
    }

    void RefeshScreen()
    {
        backgrounduri = null;
        urlbackground=null;
        avataruri=null;
        urlavatar=null;
        videos.clear();
        videoUris.clear();

        adapter.notifyDataSetChanged();
        loadingDialog.closeLoadingAlert();
    }
    private void SaveDatatoDatabase() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("BackGroundImage", urlbackground);
        data.put("PosterImage", urlavatar);
        data.put("PrimaryImage", urlavatar);
        data.put("description", description.getText().toString());
        data.put("durationTime", movieDurarion.getText().toString());
        data.put("genre", movieKind.getText().toString());
        data.put("id", document.getId());
        data.put("name", movieName.getText().toString());
        data.put("movieBeginDate", dateStart);
        data.put("vote", 0);
        data.put("trailer",InStorageVideoUris);
        document.set(data);
    }
    LocalDate localDate;
    private void showCalendarDialog() {
        // Create a calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
        if(localDate!=null)
            calendar.set(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // Create a custom DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMovieActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        // You can update the button text or perform any other actions here
                        String date = String.valueOf(selectedDay) + "/" + String.valueOf(selectedMonth + 1) + "/" + String.valueOf(selectedYear);
                        calendarButton.setText(date);
                    }
                }, year, month, dayOfMonth) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // Get the "OK" button from the dialog's layout
                Button positiveButton = getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePicker datePicker = getDatePicker();
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int dayOfMonth = datePicker.getDayOfMonth();
                        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                        localDate = LocalDate.of(year, month, dayOfMonth);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month); // Note: Calendar.MONTH is zero-based
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        dateStart = new Timestamp(calendar.getTime());
                        calendarButton.setText(date);dismiss();
                    }
                });
                // Set the desired background color for the button
                positiveButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                layoutParams.setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                positiveButton.setLayoutParams(layoutParams);
                negativeButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_background_1));
            }
        };

        // Show the dialog
        datePickerDialog.show();
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMovieActivity.this);

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.custom_layout_dialog, null);
        builder.setView(dialogView);

        // Retrieve the views from the custom layout
        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        Button okButton = dialogView.findViewById(R.id.dialog_ok_button);
        Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);
        ListView listView = dialogView.findViewById(R.id.dialog_list);

        // Set the custom background colors
        titleTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
        okButton.setBackgroundColor(getResources().getColor(R.color.main_color));
        cancelButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        // Define the list of selectable items
        String[] movieTypes = {"Horror", "Action", "Drama", "War", "Comedy", "Crime"};

        // Create the adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, movieTypes);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        AlertDialog optionDialog = builder.create();

        // Set the action buttons
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected items
                SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
                List<String> selectedItems = new ArrayList<>();
                for (int i = 0; i < checkedPositions.size(); i++) {
                    int position = checkedPositions.keyAt(i);
                    if (checkedPositions.valueAt(i)) {
                        selectedItems.add(movieTypes[position]);
                    }
                }

                // Handle selected items
                String movieKinds = selectedItems.get(0);
                for (int i = 1;i< selectedItems.size();i++) {
                    movieKinds+=',' +selectedItems.get(i);
                }
                movieKind.setText(movieKinds);
                optionDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Cancel button click
                optionDialog.dismiss();
            }
        });
        return optionDialog;
    }
    void dismissKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}





