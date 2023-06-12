package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;

import java.util.HashMap;
import java.util.Map;

public class Cinema implements Parcelable {
    private String CinemaID;
    private String CityID;
    private String Name;
    private int Price;
    private String Hotline;
    private String Image;
    private String Address;

    public Cinema(String cinemaID, String cityID, String name, String address, int price, String hotline, String image) {
        this.CinemaID = cinemaID;
        this.CityID = cityID;
        this.Name = name;
        this.Price = price;
        this.Hotline = hotline;
        this.Address = address;
        this.Image = image;
    }
    public Cinema(){}

    public String getHotline() {
        return Hotline;
    }

    public void setHotline(String hotline) {
        Hotline = hotline;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    protected Cinema(Parcel in) {
        CinemaID = in.readString();
        CityID = in.readString();
        Name = in.readString();
        Price = in.readInt();
        Address = in.readString();
        Image = in.readString();
        Hotline = in.readString();
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

    public String getAddress() {
        return Address;
    }
    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("CinemaID", CinemaID);
        json.put("CityID", CityID);
        json.put("Hotline", Hotline);
        json.put("Image", Image);
        json.put("Price", Price);
        json.put("Address", Address);
        json.put("Name", Name);
        return  json;
    };

    public void setAddress(String address) {
        Address = address;
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
        parcel.writeString(Address);
        parcel.writeString(Image);
        parcel.writeString(Hotline);

    }
}
