package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class City implements Parcelable {
    private String ID;
    private String Name;

    public City(String ID, String name) {
        this.ID = ID;
        Name = name;
    }
    public City(){};

    protected City(Parcel in) {
        ID = in.readString();
        Name = in.readString();
    }
    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("ID", ID);
        json.put("Name", Name);
        return  json;
    };

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Name);
    }
}
