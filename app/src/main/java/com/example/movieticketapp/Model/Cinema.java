package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cinema implements Parcelable {
    private String CinemaID;
    private String CityID;
    private String Name;
    private int Price;

    public Cinema(String cinemaID, String cityID, String name, int price) {
        this.CinemaID = cinemaID;
        this.CityID = cityID;
        this.Name = name;
        this.Price = price;
    }
    public Cinema(){}

    protected Cinema(Parcel in) {
        CinemaID = in.readString();
        CityID = in.readString();
        Name = in.readString();
        Price = in.readInt();
    }

    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    public String getCinemaID() {
        return CinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.CinemaID = cinemaID;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        this.CityID = cityID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(CinemaID);
        parcel.writeString(CityID);
        parcel.writeString(Name);
        parcel.writeInt(Price);
    }
}
