package com.example.movieticketapp.Model;

import kotlin.text.UStringsKt;

public class CheckoutFilmModel {
    private String name;
    private double vote;
    private String genre;

    private String duration;

    private int poster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }



    public CheckoutFilmModel(String name, double vote, String genre, String duration, int poster) {
        this.name = name;
        this.vote = vote;
        this.genre = genre;
        this.duration = duration;
        this.poster= poster;
    }

}
