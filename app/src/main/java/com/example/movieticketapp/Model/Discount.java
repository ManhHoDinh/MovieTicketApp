package com.example.movieticketapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Discount {
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
}
