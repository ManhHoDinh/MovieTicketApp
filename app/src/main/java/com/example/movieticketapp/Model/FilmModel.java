package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.movieticketapp.Adapter.TimeScheduleAdapter;
import com.google.firebase.Timestamp;

import java.util.Date;


public class FilmModel implements Parcelable {
    private String PrimaryImage;
    private String name;
    private Timestamp movieBeginDate;
    private String id;
    private  String BackGroundImage;
    private float vote;
    private String genre;
    private String description;



    private String PosterImage;
    private String durationTime;
    public FilmModel(){}



    public FilmModel(String id, String PrimaryImage, String name, String BackGroundImage, String PosterImage, float vote, String genre, String description, String durationTime, Timestamp movieBeginDate) {
        this.PrimaryImage = PrimaryImage;
        this.name = name;
        this.BackGroundImage = BackGroundImage;
        this.vote = vote;
        this.genre = genre;
        this.description = description;
        this.PosterImage = PosterImage;
        this.durationTime = durationTime;
        this.id = id;

        this.movieBeginDate = movieBeginDate;
    }

    protected FilmModel(Parcel in) {
        PrimaryImage = in.readString();
        name = in.readString();
        BackGroundImage = in.readString();
        vote = in.readFloat();
        genre = in.readString();
        description = in.readString();
        PosterImage=in.readString();
        durationTime=in.readString();
        id=in.readString();

        Date date = (Date)in.readSerializable();
        movieBeginDate = new Timestamp(date);
    }

    public Timestamp getMovieBeginDate() {
        return movieBeginDate;
    }

    public void setMovieBeginDate(Timestamp movieBeginDate) {
        this.movieBeginDate = movieBeginDate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public float getVote() {
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
        parcel.writeFloat(vote);
        parcel.writeString(genre);
        parcel.writeString(description);
        parcel.writeString(PosterImage);
        parcel.writeString(durationTime);
        parcel.writeString(id);

        parcel.writeSerializable(movieBeginDate.toDate());
    }
}