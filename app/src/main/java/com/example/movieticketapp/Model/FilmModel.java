package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.movieticketapp.databinding.PosterItemBinding;

public class FilmModel implements Parcelable {
    private String PrimaryImage;
    private String name;

    private  String BackGroundImage;
    private String vote;
    private String genre;
    private String description;

    private String PosterImage;
    private String durationTime;
    public FilmModel(){}
    public FilmModel(String PrimaryImage, String name, String BackGroundImage, String PosterImage, String vote, String genre, String description, String durationTime) {
        this.PrimaryImage = PrimaryImage;
        this.name = name;
        this.BackGroundImage = BackGroundImage;
        this.vote = vote;
        this.genre = genre;
        this.description = description;
        this.PosterImage = PosterImage;
        this.durationTime= durationTime;
    }

    protected FilmModel(Parcel in) {
        PrimaryImage = in.readString();
        name = in.readString();
        BackGroundImage = in.readString();
        vote = in.readString();
        genre = in.readString();
        description = in.readString();
        PosterImage=in.readString();
        durationTime=in.readString();
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

    public  String getDurationTime(){return  durationTime;}
    public String getPrimaryImage() {
        return PrimaryImage;
    }



    public String getBackGroundImage() {
        return BackGroundImage ;
    }
    public String getPosterImage() {
        return PosterImage ;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }
    public String getVote() {
        return vote;
    }

    public void setPrimaryImage(String image) {
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
        parcel.writeString(PrimaryImage);
        parcel.writeString(name);
        parcel.writeString(BackGroundImage);
        parcel.writeString(vote);
        parcel.writeString(genre);
        parcel.writeString(description);
        parcel.writeString(PosterImage);
        parcel.writeString(durationTime);
    }
}
