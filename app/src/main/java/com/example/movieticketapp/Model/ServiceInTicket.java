package com.example.movieticketapp.Model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class ServiceInTicket implements Serializable {
    private int count = 0;
    private String serviceID;
    ServiceInTicket(){}

    public ServiceInTicket(int count , String serviceID) {
        this.count = count;
        this.serviceID = serviceID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
