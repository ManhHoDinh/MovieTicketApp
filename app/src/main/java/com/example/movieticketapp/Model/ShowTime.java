package com.example.movieticketapp.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ShowTime {
    private String filmID;
    private String cinemaID;
    private String showtimeID;
    private List<String> bookedSeat;
    private Timestamp timeBooked;

    public ShowTime(){}

    public ShowTime(String showtimeID, String filmID, String cinemaID, List<String> bookedSeat, Timestamp timeBooked) {
        this.filmID = filmID;
        this.cinemaID = cinemaID;
        this.bookedSeat = bookedSeat;
        this.timeBooked = timeBooked;
        this.showtimeID = showtimeID;

    }

    public String getShowtimeID() {
        return showtimeID;
    }

    public void setShowtimeID(String showtimeID) {
        this.showtimeID = showtimeID;
    }

    public String getFilmID() {
        return filmID;
    }


    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
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
