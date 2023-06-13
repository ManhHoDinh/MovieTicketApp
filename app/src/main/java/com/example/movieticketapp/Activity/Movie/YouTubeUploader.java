package com.example.movieticketapp.Activity.Movie;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class YouTubeUploader {
    private static final String APPLICATION_NAME = "YourAppName";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(YouTubeScopes.YOUTUBE_UPLOAD);
    private static final String CREDENTIALS_DIRECTORY = ".credentials";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int RETRY_INTERVAL = 3000;

    private final Context context;
    private final YouTube youtube;

    public YouTubeUploader(Context context) throws GeneralSecurityException, IOException {
        this.context = context;
        this.youtube = createYouTubeClient();
    }

    private YouTube createYouTubeClient() throws GeneralSecurityException, IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(context.getAssets().open("client_secrets.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(context.getFilesDir(), CREDENTIALS_DIRECTORY)))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

        return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void uploadVideo(Uri videoUri) {
        new Thread(() -> {
            try {
                String videoPath = getVideoPathFromUri(videoUri);
                InputStreamContent mediaContent = new InputStreamContent(
                        "video/*", new FileInputStream(videoPath));

                YouTube.Videos.Insert videoInsert = youtube.videos()
                        .insert(Collections.singletonList("snippet,status"), null, mediaContent);

                Video videoObjectDefiningMetadata = new Video();
                VideoSnippet snippet = new VideoSnippet();
                snippet.setTitle("Your Video Title");
                snippet.setDescription("Your Video Description");
                snippet.setTags(Arrays.asList("tag1", "tag2"));
                videoObjectDefiningMetadata.setSnippet(snippet);

                VideoStatus status = new VideoStatus();
                status.setPrivacyStatus("private");
                videoObjectDefiningMetadata.setStatus(status);

                videoInsert.setNotifySubscribers(false);
                videoInsert.setFields("id");

                MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
                uploader.setDirectUploadEnabled(false);
                uploader.setProgressListener(new MediaHttpUploaderProgressListener() {
                    @Override
                    public void progressChanged(MediaHttpUploader uploader) throws IOException {
                        switch (uploader.getUploadState()) {
                            case INITIATION_STARTED:
                                Log.d(APPLICATION_NAME, "Upload Initiation Started");
                                break;
                            case INITIATION_COMPLETE:
                                Log.d(APPLICATION_NAME, "Upload Initiation Completed");
                                break;
                            case MEDIA_IN_PROGRESS:
                                Log.d(APPLICATION_NAME, "Upload in Progress: "
                                        + uploader.getProgress());
                                break;
                            case MEDIA_COMPLETE:
                                Log.d(APPLICATION_NAME, "Upload Completed!");
                                break;
                            case NOT_STARTED:
                                Log.d(APPLICATION_NAME, "Upload Not Started!");
                                break;
                        }
                    }
                });

                Video returnedVideo = videoInsert.execute();
                String videoId = returnedVideo.getId();

                Log.d(APPLICATION_NAME, "Video uploaded! ID: " + videoId);

                // Construct and return the video link
                Log.d(APPLICATION_NAME, "Video uploaded! ID: " + "https://www.youtube.com/watch?v="+videoId);
            } catch (Exception e) {
                Log.e(APPLICATION_NAME, "Error uploading video", e);
            }
        }).start();
    }

    private String getVideoPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }
}
