package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.movieticketapp.Adapter.TimeScheduleAdapter;
import com.google.firebase.Timestamp;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FilmModel implements Parcelable {
    private String PrimaryImage;
    private String name;
    private Timestamp movieBeginDate;

    private Timestamp movieEndDate;
    private String id;
    private  String BackGroundImage;
    private float vote;
    private String genre;
    private String description;
    private String PosterImage;
    private String durationTime;
    public FilmModel(){}

    private List<String> trailer=new ArrayList<>();
    public List<String> getTrailer()
    {
        return  trailer;
    }public void setTrailer(List<String> trailer)
    {
        this.trailer = trailer;
    }
    public FilmModel(String id, String PrimaryImage, String name, String BackGroundImage, String PosterImage, float vote, String genre, String description, String durationTime, Timestamp movieBeginDate, Timestamp movieEndDate, List<String> trailer) {
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
        this.trailer=trailer;
        this.movieEndDate= movieEndDate;
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
        movieBeginDate = new Timestamp((Date)in.readSerializable());
        movieEndDate= new Timestamp((Date)in.readSerializable());

    }

    public Timestamp getMovieBeginDate() {
        return movieBeginDate;
    }

    public void setMovieBeginDate(Timestamp movieBeginDate) {
        this.movieBeginDate = movieBeginDate;
    }
    public Timestamp getMovieEndDate() {
        return movieEndDate;
    }

    public void setMovieEndDate(Timestamp movieEndDate) {
        this.movieEndDate = movieEndDate;
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

    public void setVote(float vote) {
        this.vote = vote;
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
        parcel.writeSerializable(movieEndDate.toDate());
        }
}