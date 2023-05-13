package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Discount implements Parcelable {
    String ID;
    String Name;
    String Description;
    double DiscountRate;
    public Discount(String ID, String Name, String Description, double DiscountRate){
        this.Name = Name;
        this.DiscountRate= DiscountRate;
        this.Description=Description;
        this.ID = ID;
    }
    public Discount() {

    }
    public static String CollectionName ="Discounts";

    protected Discount(Parcel in) {
        ID = in.readString();
        Name = in.readString();
        Description = in.readString();
        DiscountRate = in.readDouble();
    }

    public static final Creator<Discount> CREATOR = new Creator<Discount>() {
        @Override
        public Discount createFromParcel(Parcel in) {
            return new Discount(in);
        }

        @Override
        public Discount[] newArray(int size) {
            return new Discount[size];
        }
    };

    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("ID", ID);
        json.put("Name", Name);
        json.put("Description", Description);
        json.put("DiscountRate", DiscountRate);
        return  json;
    };

    public String getName() {
        return Name;
    }

    public double getDiscountRate() {
        return DiscountRate;
    }

    public String getDescription() {
        return Description;
    }
    public String getID() {
        return ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Name);
        parcel.writeString(Description);
        parcel.writeDouble(DiscountRate);
    }
}
