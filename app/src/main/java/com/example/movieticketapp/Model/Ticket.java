package com.example.movieticketapp.Model;

public class Ticket {
    protected String name;
    protected String time;
    protected String studio;
    protected int poster;
    public Ticket(String ten, String time, String studio, int poster)
    {
        this.name = ten;
        this.time = time;
        this.studio = studio;
        this.poster = poster;
    }

    public String getTime() {
        return time;
    }

    public int getPoster() {
        return poster;
    }

    public String getStudio() {
        return studio;
    }

    public String getName() {
        return name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setName(String name) {
        this.name = name;
    }
}
