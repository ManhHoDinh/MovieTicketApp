package com.example.movieticketapp.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ShowTime {
    private String cinemaID;
    private String filmID;

    private List<String> bookedSeat;
    private Timestamp timeBooked;

    public ShowTime(String cinemaID, String filmID, List<String> bookedSeat, Timestamp timeBooked) {
        this.cinemaID = cinemaID;
        this.filmID = filmID;

        this.bookedSeat = bookedSeat;
        this.timeBooked = timeBooked;
    }
    public ShowTime(){}

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getFilmID() {
        return filmID;
    }

    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }





    public List<String> getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(List<String> bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public Timestamp getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(Timestamp timeBooked) {
        this.timeBooked = timeBooked;
    }
    //    private String NameCinema;
//    private String NameFilm;
//
//    private List<String> BookedSeat;
//    private Timestamp TimeBooked;
//
//    public ShowTime(){}
//
//    public ShowTime( String nameFilm, String nameCinema,List<String> bookedSeat, Timestamp timeBooked) {
//        NameCinema = nameCinema;
//        NameFilm = nameFilm;
//        BookedSeat = bookedSeat;
//        TimeBooked = timeBooked;
//    }
//
//    public String getNameCinema() {
//        return NameCinema;
//    }
//
//    public void setNameCinema(String nameCinema) {
//        NameCinema = nameCinema;
//    }
//
//    public String getNameFilm() {
//        return NameFilm;
//    }
//
//    public void setNameFilm(String nameFilm) {
//        NameFilm = nameFilm;
//    }
//
//    public List<String> getBookedSeat() {
//        return BookedSeat;
//    }
//
//    public void setBookedSeat(List<String> bookedSeat) {
//        BookedSeat = bookedSeat;
//    }
//
//    public Timestamp getTimeBooked() {
//        return TimeBooked;
//    }
//
//    public void setTimeBooked(Timestamp timeBooked) {
//        TimeBooked = timeBooked;
//    }
}
