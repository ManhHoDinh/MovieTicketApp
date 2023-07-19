package com.example.movieticketapp.Model;

import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private String UserID;
    private String Name;
    private String Email;
    private int Wallet;
    private String accountType;
    private String avatar;
    private List<String> likeComments;
    private List<String> dislikeComments;

    public static Users currentUser;

    public static boolean isExisted;

//    public Users(String UserID, String Name, String Email,int Wallet, String accountType, List<String> likeComments, List<String> dislikeComments)
//    {
//        this.UserID=UserID;
//        this.Name=Name;
//        this.Email = Email;
//        this.Wallet=Wallet;
//        this.accountType = accountType;
//        this.likeComments = likeComments;
//
//    }
    public Users(String UserID, String Name, String Email,int Wallet, String accountType, String avt, List<String> likeComments, List<String> dislikeComments )
    {
        this.UserID=UserID;
        this.Name=Name;
        this.Email = Email;
        this.Wallet=Wallet;
        this.accountType = accountType;
        this.avatar = avt;
        this.dislikeComments = dislikeComments;
        this.likeComments = likeComments;
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

    public List<String> getLikeComments() {
        return likeComments;
    }

    public void setLikeComments(List<String> likeComments) {
        this.likeComments = likeComments;
    }

    public List<String> getDislikeComments() {
        return dislikeComments;
    }

    public void setDislikeComments(List<String> dislikeComments) {
        this.dislikeComments = dislikeComments;
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
