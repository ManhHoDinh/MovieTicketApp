package com.example.movieticketapp.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ShowTime {
    private String NameFilm;
    private String NameCinema;
    private List<String> BookedSeat;
    private Timestamp TimeBooked;

    public ShowTime(String nameFilm, String nameCinema, List<String> bookedSeat, Timestamp timeBooked) {
        NameFilm = nameFilm;
        NameCinema = nameCinema;
        BookedSeat = bookedSeat;
        TimeBooked = timeBooked;
    }
    public ShowTime(){}

    public String getNameFilm() {
        return NameFilm;
    }

    public void setNameFilm(String nameFilm) {
        NameFilm = nameFilm;
    }

    public String getNameCinema() {
        return NameCinema;
    }

    public void setNameCinema(String nameCinema) {
        NameCinema = nameCinema;
    }

    public List<String> getBookedSeat() {
        return BookedSeat;
    }

    public void setBookedSeat(List<String> bookedSeat) {
        BookedSeat = bookedSeat;
    }

    public Timestamp getTimeBooked() {
        return TimeBooked;
    }

    public void setTimeBooked(Timestamp timeBooked) {
        TimeBooked = timeBooked;
    }
}
