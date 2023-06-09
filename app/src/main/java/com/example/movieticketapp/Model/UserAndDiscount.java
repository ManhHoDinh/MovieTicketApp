package com.example.movieticketapp.Model;

public class UserAndDiscount {
    private String userID;
    private String discountID;

    public UserAndDiscount(String userID, String discountID) {
        this.userID = userID;
        this.discountID = discountID;
    }
    public static String collectionName ="UserAndDiscount";
    public UserAndDiscount(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }
}
