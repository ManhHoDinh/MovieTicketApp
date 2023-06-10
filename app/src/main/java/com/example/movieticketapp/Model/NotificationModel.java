package com.example.movieticketapp.Model;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NotificationModel {
    String ID;
    Timestamp PostTime;
    String Heading;
    String Description;
    String PostAuthor;
    public  static String CollectionName="Notifications";
    public NotificationModel(){}
    public NotificationModel(String ID, String Heading, String Description, String PostAuthor, Timestamp PostTime){
        this.Heading=Heading;
        this.ID=ID;
        this.Description=Description;
        this.PostAuthor=PostAuthor;
        this.PostTime=PostTime;
    }
    public  void setDescription(String Description)
    {
        this.Description = Description;
    }
    public String getDescription(){return Description;}
    public  void setHeading(String Heading)
    {
        this.ID = Heading;
    }
    public String getHeading(){return Heading;}
    public  void setPostAuthor(String PostAuthor)
    {
        this.PostAuthor = PostAuthor;
    }
    public String getPostAuthor(){return PostAuthor;}
    public  void setPostTime(Timestamp PostTime)
    {
        this.PostTime = PostTime;
    }
    public Timestamp getPostTime(){return PostTime;}
    public  void setID(String ID)
    {
        this.ID = ID;
    }
    public String getID(){return ID;}
    public Map<String, Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("ID", ID);
        json.put("Heading", Heading);
        json.put("Description", Description);
        json.put("PostTime", PostTime);
        json.put("PostAuthor", PostAuthor);
        return  json;
    };

}
