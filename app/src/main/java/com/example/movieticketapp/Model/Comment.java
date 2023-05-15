package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Comment implements Parcelable {
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
    protected Comment(Parcel in) {
        profileUrl = in.readString();
        name = in.readString();
        reviewText = in.readString();
        like = in.readString();
        dislike = in.readString();
        timeStamp = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(profileUrl);
        parcel.writeString(name);
        parcel.writeString(reviewText);
        parcel.writeString(like);
        parcel.writeString(dislike);
        parcel.writeString(timeStamp);
    }
}
