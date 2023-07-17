package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.List;

public class Comment {
    private String profileUrl;
    private int rating;
    private String userId;
    private String reviewText;

    private int like;

    private int dislike;

    private Timestamp timeStamp;
    private List<String> listReact;
    private String ID;

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
//    public Date getDateTime() {
//        return timeStamp.;
//    }
//
//    public void setDateTime(Date datetime) {
//        this.dateTime = datetime;
//    }
//
//    @Override
//    public int compareTo(MyObject o) {
//        return getDateTime().compareTo(o.getDateTime());
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Comment(){}
    public Comment(String profileUrl, String reviewText, int like, int dislike, Timestamp timeStamp, int rating, List<String> listReact, String ID, String userId) {
        this.profileUrl = profileUrl;

        this.reviewText = reviewText;
        this.like = like;
        this.dislike = dislike;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.listReact = listReact;
        this.ID = ID;
        this.userId = userId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<String> getListReact() {
        return listReact;
    }

    public void setListReact(List<String> listReact) {
        this.listReact = listReact;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
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
//    protected Comment(Parcel in) {
//        profileUrl = in.readString();
//        name = in.readString();
//        reviewText = in.readString();
//        like = in.readString();
//        dislike = in.readString();
//        timeStamp = in.readString();
//    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
//        @Override
//        public Comment createFromParcel(Parcel in) {
//            return new Comment(in);
//        }
//
//        @Override
//        public Comment[] newArray(int size) {
//            return new Comment[size];
//        }
//    };
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        parcel.writeString(profileUrl);
//        parcel.writeString(name);
//        parcel.writeString(reviewText);
//        parcel.writeString(like);
//        parcel.writeString(dislike);
//        parcel.write
//    }
}
