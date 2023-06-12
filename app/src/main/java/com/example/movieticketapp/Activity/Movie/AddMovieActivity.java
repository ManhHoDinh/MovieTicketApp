package com.example.movieticketapp.Activity.Movie;

import static androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeScopes;
//import com.google.api.services.youtube.model.Video;
//import com.google.api.services.youtube.model.VideoSnippet;
//import com.google.api.services.youtube.model.VideoStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;




public class AddMovieActivity extends AppCompatActivity {
    private static final String CLIENT_SECRETS_FILE = "client_secret.json";
    private static final String APPLICATION_NAME = "Your Application Name";
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
    Button statusmovie;
    String status;
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
        movieKind = (EditText) findViewById(R.id.movieKind);
        movieDurarion =(EditText) findViewById(R.id.movieDuration);
        applyButton = (Button) findViewById(R.id.applybutton);
        cancleButton = (Button) findViewById(R.id.cancelbutton);

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
                                        movietrailer.setBackground(null);
                                        movietrailer.setVideoURI(uri);
                                        traileruri = uri;
                                        movietrailer.start();

                                        MediaController mediaController = new MediaController(this);
                                        movietrailer.setMediaController(mediaController);
                                        mediaController.setAnchorView(movietrailer);
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
        movietrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pickMedia.launch(new PickVisualMediaRequest.Builder()
//                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
//                        .build());
                th = 3;
                texttrailer.setText("");
                imtrailer.setImageResource(0);
            }
        });
//        statusmovie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShowMenu();
//            }
//        });
//        applyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (movieName.length() == 0) {
//                    movieName.setError("Movie Name cannot be empty!!!");
//                    error = true;
//                }
//                if (movieKind.length() == 0) {
//                    movieKind.setError("Movie Kind cannot be empty!!!");
//                    error = true;
//                }
//                if (movieDurarion.length() == 0) {
//                    movieDurarion.setError("Movie Duration cannot be empty!!!");
//                    error = true;
//                }
//                if (status.length() == 0){
//                    error = true;
//                }
//
//
//                if (!error)
//                {
//                    Calendar calFordData = Calendar.getInstance();
//                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
//                    String saveCurrentData = currentDate.format(calFordData.getTime());
//
//                    Calendar calFordTime = Calendar.getInstance();
//                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//                    String saveCurrentTime = currentTime.format(calFordData.getTime());
//
//                    String postRandomName = saveCurrentData + saveCurrentTime;
//
//                    storageReference = storageReference.child(postRandomName+".jpg");
//                    uploadTask = storageReference.putFile(backgrounduri);
//                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//
//                            // Continue with the task to get the download URL
//                            return storageReference.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                urlbackground = task.getResult().toString();
//                                SaveDatatoDatabase();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    storageReference2 = storageReference2.child(postRandomName+"th2.jpg");
//                    uploadTask2 = storageReference2.putFile(avataruri);
//                    Task<Uri> urlTask2 = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//
//                            // Continue with the task to get the download URL
//                            return storageReference2.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                urlavatar = task.getResult().toString();
//                                SaveDatatoDatabase();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "ERRROR!!!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//                else
//                {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Have some errors!!!", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            }
//        });
//        cancleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
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
//    private void SaveDatatoDatabase() {
//        document = databaseReference.document("Movies/"+movieName.getText().toString());
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("BackGroundImage", urlbackground);
//        data.put("PosterImage", urlbackground);
//        data.put("PrimaryImage", urlavatar);
//        data.put("description", description.getText().toString());
//        data.put("durationTime", movieDurarion.getText().toString());
//        data.put("genre", movieKind.getText().toString());
//        data.put("id", movieName.getText().toString());
//        data.put("name", movieName.getText().toString());
//        data.put("status", status);
//        data.put("vote", "0");
//        document.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(getApplicationContext(), "Add Movie Success!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
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
                        // selectedYear, selectedMonth, and selectedDay contain the selected date
                    }
                }, year, month, dayOfMonth) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // Get the "OK" button from the dialog's layout
                Button positiveButton = getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = getButton(DialogInterface.BUTTON_NEGATIVE);

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
        calendarButton.setText(String.valueOf(year));
    }

//    private void uploadVideoToYouTube(Uri videoUri) {
//        // Khởi tạo YouTube API client
//        YouTube youtube = null;
//        try {
//            youtube = buildYouTubeClient();
//        } catch (IOException | GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//
//        // Tạo một đối tượng Video để đại diện cho video sẽ được tải lên
//        MediaStore.Video video = new MediaStore.Video();
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
//            e.printStackTrace();
//        }
//
//        try {
//            // Tạo yêu cầu API để tải lên video
//            YouTube.Videos.Insert videoInsert = youtube.videos()
//                    .insert("snippet,status", video, mediaContent);
//
//            // Thiết lập cấu hình tải lên
//            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
//            uploader.setDirectUploadEnabled(false);
//            uploader.setProgressListener(new MediaHttpUploaderProgressListener() {
//                @Override
//                public void progressChanged(MediaHttpUploader uploader) throws IOException {
//                    switch (uploader.getUploadState()) {
//                        case INITIATION_STARTED:
//                            System.out.println("Initiation Started");
//                            break;
//                        case INITIATION_COMPLETE:
//                            System.out.println("Initiation Completed");
//                            break;
//                        case MEDIA_IN_PROGRESS:
//                            System.out.println("Upload in progress: " + uploader.getProgress());
//                            break;
//                        case MEDIA_COMPLETE:
//                            System.out.println("Upload Completed!");
//                            break;
//                        case NOT_STARTED:
//                            System.out.println("Upload Not Started!");
//                            break;
//                    }
//                }
//            });
//
//            // Thực hiện yêu cầu API để tải lên video
//            MediaStore.Video returnedVideo = videoInsert.execute();
//
//            // Lấy ID của video đã tải lên
//            String videoId = returnedVideo.getId();
//            System.out.println("Video upload successful! Video ID: " + videoId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private YouTube buildYouTubeClient() throws IOException, GeneralSecurityException {
//        HttpTransport httpTransport = new NetHttpTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//
//        // Đường dẫn đến tệp client_secret.json
//        InputStream in = new FileInputStream(CLIENT_SECRETS_FILE);
//        GoogleCredential credential = GoogleCredential.fromStream(in)
//                .createScoped(Collections.singleton(YouTubeScopes.YOUTUBE_UPLOAD))
//                .createDelegated("your_account@example.com"); // Thay thế bằng email của tài khoản Google
//
//        // Xây dựng YouTube API client
//        return new YouTube.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
}





