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
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
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

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.ServiceAdapter;
import com.example.movieticketapp.Adapter.TrailerMovieApdapter;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.NetworkChangeListener;
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
import com.squareup.picasso.Picasso;

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
    Button applyButton;
    Button cancleButton;
    Uri backgrounduri;
    Uri avataruri = null;

    String urlbackground;
    Timestamp BeginDate;
    Timestamp EndDate;
    String urlavatar;
    Button BeginDateCalendarButton;
    Button EndDateCalendarButton;
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
        BeginDateCalendarButton = findViewById(R.id.BeginDateCalendar);
        EndDateCalendarButton= findViewById(R.id.EndDateCalendar);
        document = databaseReference.collection("Movies").document();

        BeginDateCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show calendar dialog
                showBeginDateCalendarDialog();
                dismissKeyboard(v);
            }
        });
        EndDateCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show calendar dialog
                showEndDateCalendarDialog();
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
                dismissKeyboard(view);
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
                clearFocus();
                dismissKeyboard();
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                th = 0;
            }
        });
        movieavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFocus();
                dismissKeyboard();
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
                int totalUploadTasks = 2 + videos.size();
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
                if(BeginDate==null)
                {
                    error=true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Chose Start Date, Please!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if(EndDate==null)
                {
                    error=true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Chose End Date, Please!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if(EndDate!=null && BeginDate!=null)
                {
                    if(EndDate.toDate().before(BeginDate.toDate()))
                    {
                        error=true;
                        Toast toast = Toast.makeText(getApplicationContext(), "Begin date must be earlier than end date!!!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                if (!error)
                {
                    loadingDialog.StartAlertDialog();

                    String folder = "Movies/"+document.getId(); // Replace "your_folder_name" with the desired folder name
                    String BackgroundName = folder + "/Background" ;
                    MediaManager.get().upload(backgrounduri).option("public_id", BackgroundName)
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
                                    String url = (String) resultData.get("secure_url");
                                    urlbackground = url;
                                    SaveDatatoDatabase();
                                    if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                    RefeshScreen();
                                    finish();
                                    Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    }
                                }
                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                        RefeshScreen();
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    // your code here
                                }})
                            .dispatch();
                    String PrimaryName = folder + "/Primary" ;
                    MediaManager.get().upload(avataruri).option("public_id", PrimaryName)
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
                                    String url = (String) resultData.get("secure_url");
                                    urlavatar = url;
                                    SaveDatatoDatabase();
                                    if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                        RefeshScreen();
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                        RefeshScreen();
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    // your code here
                                }})
                            .dispatch();

                    for(int i = 0; i < AddMovieActivity.videoUris.size();i++)
                    {
                        Map<String, Object> options = new HashMap<>();
                        options.put("public_id", folder + "/video"+String.valueOf(i));
                        options.put("resource_type", "video");
                        MediaManager.get().upload(AddMovieActivity.videoUris.get(i)).options(options).option("chunk_size", 10000000)
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
                                        String url = (String) resultData.get("secure_url");
                                        InStorageVideoUris.add(url);
                                        SaveDatatoDatabase();
                                        if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                            RefeshScreen();
                                            finish();
                                            Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    }
                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {
                                        // your code here
                                        if (completedUploadTasks.incrementAndGet() == totalUploadTasks) {
                                            RefeshScreen();
                                            finish();
                                            Toast toast = Toast.makeText(getApplicationContext(),"Add movie success!!!", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    }
                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {
                                        // your code here
                                    }})
                                .dispatch();
                    }
                }
                else
                {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Have some errors!!!", Toast.LENGTH_SHORT);
//                    toast.show();
                }

            }

        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                finish();
                RefeshScreen();
            }
        });
    }
    @Override
    public void onBackPressed() {
        AddMovieActivity.videos.clear();
        AddMovieActivity.videoUris.clear();
        super.onBackPressed();
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
        data.put("movieBeginDate", BeginDate);
        data.put("movieEndDate",EndDate);
        data.put("vote", 0);
        data.put("trailer",InStorageVideoUris);
        document.set(data);
    }
    LocalDate localBeginDate;
    LocalDate localEndDate;
    private void showBeginDateCalendarDialog() {
        // Create a calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
        if(localBeginDate!=null)
            calendar.set(localBeginDate.getYear(),localBeginDate.getMonthValue()-1,localBeginDate.getDayOfMonth());
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
                        BeginDateCalendarButton.setText(date);
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
                        localBeginDate = LocalDate.of(year, month+1, dayOfMonth);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month); // Note: Calendar.MONTH is zero-based
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        BeginDate = new Timestamp(calendar.getTime());
                        BeginDateCalendarButton.setText(date);dismiss();
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
    private void showEndDateCalendarDialog() {
        // Create a calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
        if(localEndDate!=null)
            calendar.set(localEndDate.getYear(),localEndDate.getMonthValue()-1,localEndDate.getDayOfMonth());
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
                        EndDateCalendarButton.setText(date);
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
                        localEndDate = LocalDate.of(year, month+1, dayOfMonth);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month); // Note: Calendar.MONTH is zero-based
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        EndDate = new Timestamp(calendar.getTime());
                        EndDateCalendarButton.setText(date);dismiss();
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
        InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    private void clearFocus() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}




