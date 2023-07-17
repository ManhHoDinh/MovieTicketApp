package com.example.movieticketapp.Model;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class Users {
    private String UserID;
    private String Name;
    private String Email;
    private int Wallet;
    private String accountType;
    private String avatar;
    public static Users currentUser;

    public static boolean isExisted;
    public Users(String UserID, String Name, String Email,int Wallet, String accountType)
    {
        this.UserID=UserID;
        this.Name=Name;
        this.Email = Email;
        this.Wallet=Wallet;
        this.accountType = accountType;
    }
    public Users(String UserID, String Name, String Email,int Wallet, String accountType, String avt)
    {
        this.UserID=UserID;
        this.Name=Name;
        this.Email = Email;
        this.Wallet=Wallet;
        this.accountType = accountType;
        this.avatar = avt;
    }
    public  Users(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("UserID", UserID);
        json.put("Name", Name);
        json.put("Email", Email);
        json.put("Wallet", Wallet);
        json.put("accountType", accountType);
        json.put("avatar", avatar);
        return  json;
    };
    public String getAccountType(){
        return  accountType;
    };
    public String getEmail(){
        return  Email;
    };
}
