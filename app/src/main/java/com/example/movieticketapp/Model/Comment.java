package com.example.movieticketapp.Model;

public class Comment {
    private String profileUrl;

    private String name;

    private String reviewText;

    private int like;

    private int dislike;

    public Comment(String profileUrl, String name, String reviewText, int like, int dislike) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.reviewText = reviewText;
        this.like = like;
        this.dislike = dislike;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
}
