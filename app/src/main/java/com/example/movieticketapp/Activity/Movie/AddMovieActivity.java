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

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class AddMovieActivity extends AppCompatActivity {
    private static final String APPLICATION_NAME = "M";
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
    TextView movieKind;
    EditText movieDurarion;
    Button statusmovie;
    String status;
    RoundedImageView movieactor;
    ImageView imcast;
    TextView textcast;
    public static Context context;
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
    String videoUrl="";

    UploadTask uploadTask;
    UploadTask uploadTask2;
    Button calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_screen);
        calendarButton = findViewById(R.id.Calendar);

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

        movieavatar = (RoundedImageView) findViewById(R.id.movieavatar);
        textavt = (TextView) findViewById(R.id.textavt);
        imavt = (ImageView) findViewById(R.id.imavt);

        description = (EditText) findViewById(R.id.MovieDescription);
        movieName = (EditText) findViewById(R.id.movieName);
        movieKind = (TextView) findViewById(R.id.movieKind);
        movieDurarion =(EditText) findViewById(R.id.movieDuration);
        applyButton = (Button) findViewById(R.id.applybutton);
        cancleButton = (Button) findViewById(R.id.cancelbutton);

        movietrailer = (VideoView) findViewById(R.id.movietrailer);
        imtrailer = (ImageView) findViewById(R.id.imtrailer);
        texttrailer = (TextView) findViewById(R.id.texttrailer);
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
        LinearLayoutCompat containerLayout = findViewById(R.id.containerLayout);
        Button addButton = findViewById(R.id.addButton);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(AddMovieActivity.this);
                View layout = inflater.inflate(R.layout.trailer_movie, containerLayout, false);
                containerLayout.addView(layout);
                containerLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        // Set the width of LinearLayoutCompat to its total measured width
                        containerLayout.getLayoutParams().width = containerLayout.getMeasuredWidth();
                        containerLayout.requestLayout();

                        // Scroll to the end of the HorizontalScrollView
                        horizontalScrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            }
                        });
                    }
                });
            }
        });

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
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
                                    case 2:
                                        movietrailer.setBackground(null);
                                        movietrailer.setVideoURI(uri);
                                        traileruri = uri;
                                        movietrailer.start();

                                        MediaController mediaController = new MediaController(this);
                                        movietrailer.setMediaController(mediaController);
                                        mediaController.setAnchorView(movietrailer);
                                        if(uri!=null)
                                        {
                                            texttrailer.setText("");
                                            imtrailer.setImageResource(0);
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
        movietrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                        .build());
                th = 2;
            }
        });
//        statusmovie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShowMenu();
//            }
//        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                boolean error = false;

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

                if (!error)
                {
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
                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    StorageReference VideoStorageReference= FirebaseStorage.getInstance().getReference().child("Movies/"+MovieName+"/"+MovieName+"Video.mp4");
                    VideoStorageReference.putFile(traileruri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                                videoUrl = task.getResult().toString();
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

//            @Override
//            public void onClick(View view) {
//                // Initialize the YouTubeUploader class
//                final VideoView videoView = findViewById(R.id.Demo); //id in your xml file
//                videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/movie-ticket-app-0.appspot.com/o/video%2Fvideoplayback.mp4?alt=media&token=4ffbea2f-9a58-435a-94a0-eccc48f09798")); //the string of the URL mentioned above
//                videoView.requestFocus();
//                videoView.start();
//                MediaController mediaController = new MediaController(AddMovieActivity.this);
//                videoView.setMediaController(mediaController);
//                mediaController.setAnchorView(videoView);
//            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                finish();
            }
        });
    }
//    private void ShowMenu(){
//        PopupMenu pm = new PopupMenu(this, statusmovie);
//        pm.getMenuInflater().inflate(R.menu.status_movie_menu, pm.getMenu());
//        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.itemplaying:
//                        status = "playing";
//                        statusmovie.setText("Now Playing");
//                        break;
//                    case R.id.itemcoming:
//                        status = "coming";
//                        statusmovie.setText("Coming Soon!");
//                        break;
//                }
//                return false;
//            }
//        });
//        pm.show();
//    }
//
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
        data.put("movieBeginDate", Timestamp.now());
        data.put("vote", 0);
        data.put("trailer",videoUrl);
        document.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Add Movie Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    int y = 0;
    int m = 0;
    int d = 0;

    private void showCalendarDialog() {
        // Create a calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
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

//    private void uploadVideoToYouTube(Uri videoUri) {
//        // Khởi tạo YouTube API client
//        YouTube youtube = null;
//
//        try {
//            youtube = buildYouTubeClient();
//        } catch (IOException | GeneralSecurityException e) {
//            Log.d("Error"+e.getMessage(),e.getMessage());
//        }
//
//        // Tạo một đối tượng Video để đại diện cho video sẽ được tải lên
//        Video video = new Video();
//
//        // Thiết lập thông tin về video (tiêu đề, mô tả, v.v.)
//        VideoSnippet snippet = new VideoSnippet();
//        snippet.setTitle("Your Video Title");
//        snippet.setDescription("Your Video Description");
//        video.setSnippet(snippet);
//
//        // Thiết lập trạng thái của video (công khai, riêng tư, v.v.)
//        VideoStatus status = new VideoStatus();
//        status.setPrivacyStatus("private"); // Có thể thay đổi giá trị này
//        video.setStatus(status);
//
//        // Tạo nội dung video từ đường dẫn Uri
//        InputStreamContent mediaContent = null;
//        try {
//            InputStream videoInputStream = getContentResolver().openInputStream(videoUri);
//            mediaContent = new InputStreamContent("video/*", videoInputStream);
//        } catch (IOException e) {
//            Log.d("Error 2 "+e.getMessage(),e.getMessage());
//        }
//
//        try {
//            // Tạo yêu cầu API để tải lên video
//            YouTube.Videos.Insert videoInsert;
//            if(youtube!=null)
//            {
//                videoInsert = youtube.videos()
//                        .insert(Collections.singletonList("snippet,status"), video, mediaContent);
//
//                // Thiết lập cấu hình tải lên
//                MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
//                uploader.setDirectUploadEnabled(false);
//                uploader.setProgressListener(new MediaHttpUploaderProgressListener() {
//                    @Override
//                    public void progressChanged(MediaHttpUploader uploader) throws IOException {
//                        switch (uploader.getUploadState()) {
//                            case INITIATION_STARTED:
//                                System.out.println("Initiation Started");
//                                break;
//                            case INITIATION_COMPLETE:
//                                System.out.println("Initiation Completed");
//                                break;
//                            case MEDIA_IN_PROGRESS:
//                                System.out.println("Upload in progress: " + uploader.getProgress());
//                                break;
//                            case MEDIA_COMPLETE:
//                                System.out.println("Upload Completed!");
//                                break;
//                            case NOT_STARTED:
//                                System.out.println("Upload Not Started!");
//                                break;
//                        }
//                    }
//                });
//
//                // Thực hiện yêu cầu API để tải lên video
//                Video returnedVideo = videoInsert.execute();
//
//                // Lấy ID của video đã tải lên
//                String videoId = returnedVideo.getId();
//                System.out.println("Video upload successful! Video ID: " + videoId);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void buildYouTubeClient() {
//        new AsyncTask<Void, Void, YouTube>() {
//            @Override
//            protected YouTube doInBackground(Void... voids) {
//                try {
//                    HttpTransport httpTransport = new NetHttpTransport();
//                    JsonFactory jsonFactory = new JacksonFactory();
//                    Resources res = getResources();
//                    InputStream in = res.openRawResource(R.raw.client_secret);
//
//                    Log.d("Not Error", "Not Error");
//
//                    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
//
//                    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                            httpTransport, jsonFactory, clientSecrets, Collections.singleton(YouTubeScopes.YOUTUBE))
//                            .build();
//
//                    // Perform the OAuth2 authorization process
//                    Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//
//                    // Build YouTube API client
//                    Log.d("Not Error", "Not Error");
//                    return new YouTube.Builder(httpTransport, jsonFactory, credential)
//                            .setApplicationName(APPLICATION_NAME)
//                            .build();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            protected void onPostExecute(YouTube youTube) {
//                if (youTube != null) {
//                    // Use the YouTube client here
//                } else {
//                    // Handle the case when YouTube client creation fails
//                }
//            }
//        }.execute();
//    }

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
        titleTextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        okButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        cancelButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        // Define the list of selectable items
        String[] movieTypes = {"All", "Horror", "Action", "Drama", "War", "Comedy", "Crime"};

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





