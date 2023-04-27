package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.movieticketapp.databinding.PosterItemBinding;

public class FilmModel implements Parcelable {
    private int PrimaryImage;
    private String name;

    private  int BackGroundImage;
    private double vote;
    private String director;
    private String description;

    private int PosterImage;
    private String durationTime;
    public FilmModel(int PrimaryImage, String name, int BackGroundImage,int PosterImage, double vote, String director, String description, String durationTime) {
        this.PrimaryImage = PrimaryImage;
        this.name = name;
        this.BackGroundImage = BackGroundImage;
        this.vote = vote;
        this.director = director;
        this.description = description;
        this.PosterImage = PosterImage;
        this.durationTime= durationTime;
    }

    protected FilmModel(Parcel in) {
        PrimaryImage = in.readInt();
        name = in.readString();
        BackGroundImage = in.readInt();
        vote = in.readDouble();
        director = in.readString();
        description = in.readString();
        PosterImage=in.readInt();
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
    public int getPrimaryImage() {
        return PrimaryImage;
    }

    public int getBackGroundImage() {
        return BackGroundImage ;
    }
    public int getPosterImage() {
        return PosterImage ;
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
        parcel.writeInt(BackGroundImage);
        parcel.writeDouble(vote);
        parcel.writeString(director);
        parcel.writeString(description);
        parcel.writeInt(PosterImage);
        parcel.writeString(durationTime);
    }
}
