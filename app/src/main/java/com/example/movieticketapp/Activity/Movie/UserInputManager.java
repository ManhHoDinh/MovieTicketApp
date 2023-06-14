package com.example.movieticketapp.Activity.Movie;


import java.util.List;
import java.util.Scanner;

public class UserInputManager {

    YoutubeUploader youtubeUploader;

    private String categoryID;
    private String description;
    private String title;
    private String privacyStatus;
    private List<String> tags;

    public boolean getUserInputFromCommandLine(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Title:");
        setTitle(scan.nextLine());

        System.out.println("Enter Description:");
        setDescription(scan.nextLine());

        System.out.println("Current Youtube Categories:" + youtubeUploader.getUserCategories() + "\n");
        System.out.println("Enter CategoryID:");
        setCategoryID(scan.nextLine());

        System.out.println("Set Privacy Status: (PRIVATE, public, unlisted)");
        String status = scan.nextLine().trim();
        if(!status.equalsIgnoreCase("private") &&
                !status.equalsIgnoreCase("public") &&
                !status.equalsIgnoreCase("unlisted")){
            System.out.println("Unsupported status, make sure spelling is correct. " +
                    "Video will be marked private by default");
            status="private";
        }
        setPrivacyStatus(status);

        //TODO: Tags

        System.out.println("Is the information correct? (y/N)");
        String correct = scan.nextLine();
        if(correct.equalsIgnoreCase("y")){
            System.out.println("Ready to upload? (y/N)");
            return scan.nextLine().equalsIgnoreCase("y");
        }
        else{
            return false;
        }
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(String privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}