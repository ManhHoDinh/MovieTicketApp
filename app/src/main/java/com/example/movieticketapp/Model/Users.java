package com.example.movieticketapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Users {
    private String UserID;
    private String Name;
    private String Email;
    public Users(String UserID, String Name, String Email)
    {
        this.UserID=UserID;
        this.Name=Name;
        this.Email = Email;
    }
    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("UserID", UserID);
        json.put("Name", Name);
        json.put("Email", Email);
        return  json;
    };

}
