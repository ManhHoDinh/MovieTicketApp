package com.example.movieticketapp.Model;

public class Comment {
    private String profileUrl;

    private String name;

    private String reviewText;

    private String like;

    private String dislike;

    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Comment(String profileUrl, String name, String reviewText, String like, String dislike, String timeStamp) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.reviewText = reviewText;
        this.like = like;
        this.dislike = dislike;
        this.timeStamp = timeStamp;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }
}
