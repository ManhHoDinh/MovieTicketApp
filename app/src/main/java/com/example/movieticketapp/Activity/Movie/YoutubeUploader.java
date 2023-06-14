package com.example.movieticketapp.Activity.Movie;

import com.example.movieticketapp.Activity.Movie.UserInputManager;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;


import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoCategoryListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class YoutubeUploader {
    UserInputManager userInputManager;

    @Value("${applicationName}")
    private String APPLICATION_NAME;

    @Value("${clientSecretPath}")
    private String CLIENT_SECRETS;

    @Value("${authDataDirectory}")
    private String AUTH_DATA_DIR;

    @Value("${developerKeyPath}")
    public String DEVELOPER_KEY_PATH;

    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.upload",
                    "https://www.googleapis.com/auth/youtube.readonly");

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setAccessType("offline")
                        .setDataStoreFactory(new FileDataStoreFactory(new File(AUTH_DATA_DIR)))
                        .build();
        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String getUserCategories(){
        String returnString="";
        VideoCategoryListResponse response = null;
        try {
            YouTube.VideoCategories.List request = this.getService().videoCategories()
                    .list(Collections.singletonList("")).setKey(this.getDeveloperKeyFromFile()).setPart(Collections.singletonList("snippet")).setRegionCode("US");
            response = request.execute();
            for(int i = 0; i<response.getItems().size(); i++){
                String ID = response.getItems().get(i).getId();
                String title = response.getItems().get(i).getSnippet().getTitle();
                returnString+="ID: " + ID + " Description: " + title + " \n";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(returnString);
    }

    public void gatherUserInputForVideo(){
        if(!userInputManager.getUserInputFromCommandLine()){
            System.err.println("Please rerun program with correct input");
            System.exit(2);
        }
    }

    public String getDeveloperKeyFromFile(){
        String developerKey="";
        try {
            BufferedReader developerKeyFileReader = new BufferedReader(new FileReader(new File(DEVELOPER_KEY_PATH)));
            developerKey = developerKeyFileReader.readLine();
        }
        catch(Exception e){
            System.err.println("Please check that developer key file is in config directory.");
            e.printStackTrace();
            System.exit(1);
        }

        return developerKey;
    }

    public void uploadVideo(String videoPath, String developerKey) throws IOException, GeneralSecurityException {

        YouTube youtubeService = this.getService();

        // Define the Video object, which will be uploaded as the request body.
        Video video = new Video();

        // Add the snippet object property to the Video object.
        VideoSnippet snippet = new VideoSnippet();
        snippet.setCategoryId(userInputManager.getCategoryID());
        snippet.setDescription(userInputManager.getDescription());
        snippet.setTitle(userInputManager.getTitle());
        video.setSnippet(snippet);

        // Add the status object property to the Video object.
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(userInputManager.getPrivacyStatus());
        video.setStatus(status);

        File mediaFile = new File(videoPath);
        InputStreamContent mediaContent =
                new InputStreamContent("video/*",
                        new BufferedInputStream(new FileInputStream(mediaFile)));
        mediaContent.setLength(mediaFile.length());

        // Define and execute the API request
        YouTube.Videos.Insert request = youtubeService.videos()
                .insert(Collections.singletonList("snippet,status"), video, mediaContent).setKey(developerKey);
        Video response = request.execute();
        System.out.println(response);
    }
}