package com.example.movieticketapp.Model;

import kotlin.text.UStringsKt;

public class CheckoutFilmModel {
    private String name;
    private Float vote;
    private String genre;

    private String duration;

    private String poster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getVote() {
        return vote;
    }

    public void setVote(Float vote) {
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }



    public CheckoutFilmModel(String name, Float vote, String genre, String duration, String poster) {
        this.name = name;
        this.vote = vote;
        this.genre = genre;
        this.duration = duration;
        this.poster= poster;
    }

}
