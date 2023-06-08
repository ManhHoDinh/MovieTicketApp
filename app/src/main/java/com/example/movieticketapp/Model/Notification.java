package com.example.movieticketapp.Model;

import java.sql.Timestamp;

public class Notification {
    Timestamp PostTime;
    String Heading;
    String Description;
    String PostAuthor;
    public Notification(){}
    public Notification(String Heading, String Description, String PostAuthor, Timestamp PostTime){
        this.Heading=Heading;
        this.Description=Description;
        this.PostAuthor=PostAuthor;
        this.PostTime=PostTime;
    }
    public String getDescription(){return Description;}
    public String getHeading(){return Heading;}
    public String getPostAuthor(){return PostAuthor;}
    public Timestamp getPostTime(){return PostTime;}
}
