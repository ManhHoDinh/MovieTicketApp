package com.example.movieticketapp.Model;

public class Discount {
    String Name;
    String Description;
    double DiscountRate;
    public Discount(String Name, String Description, double DiscountRate){
        this.Name = Name;
        this.DiscountRate= DiscountRate;
        this.Description=Description;
    }
    public Discount() {

    }

    public String getName() {
        return Name;
    }

    public double getDiscountRate() {
        return DiscountRate;
    }

    public String getDescription() {
        return Description;
    }
}
