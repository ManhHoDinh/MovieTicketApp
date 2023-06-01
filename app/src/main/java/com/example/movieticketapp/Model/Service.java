package com.example.movieticketapp.Model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Service implements Parcelable {
    String detail;
    String image;
    String name;
    int price;
    String ID;

    public Service() {}

    public Service(String detail, String image, String name, int price, String ID) {
        this.detail = detail;
        this.image = image;
        this.name = name;
        this.price = price;
        this.ID = ID;
    }

    protected Service(Parcel in) {
        detail = in.readString();
        image = in.readString();
        name = in.readString();
        price = in.readInt();
        ID = in.readString();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("detail", detail);
        json.put("image", image);
        json.put("name", name);
        json.put("price", price);
        json.put("ID", ID);
        return  json;
    };

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(detail);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(ID);
    }
}
