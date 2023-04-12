package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FilmModel implements Parcelable {
    private int PrimaryImage;
    private String name;

    private  int BackGroundImage;
    private double vote;
    private String director;
    private String description;
    public FilmModel(int PrimaryImage, String name, int BackGroundImage, double vote, String director, String description) {
        this.PrimaryImage = PrimaryImage;
        this.name = name;
        this.BackGroundImage = BackGroundImage;
        this.vote = vote;
        this.director = director;
        this.description = description;
    }

    protected FilmModel(Parcel in) {
        PrimaryImage = in.readInt();
        name = in.readString();
    }

    public static final Creator<FilmModel> CREATOR = new Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel in) {
            return new FilmModel(in);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
        }
    };

    public int getPrimaryImage() {
        return PrimaryImage;
    }

    public int getBackGroundImage() {
        return BackGroundImage ;
    }

    public String getName() {
        return name;
    }


    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }
    public double getVote() {
        return vote;
    }

    public void setPrimaryImage(int image) {
        this.PrimaryImage = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(PrimaryImage);
        parcel.writeString(name);
    }
}
