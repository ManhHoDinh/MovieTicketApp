package com.example.movieticketapp.Model;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFilm {
    private static ScheduleFilm instance;
    public String dateBooked;
    public String cinemaName;
    public List<ShowTime> listShowTime = new ArrayList<ShowTime>();

    public static ScheduleFilm getInstance() {
        if (instance == null) {
            instance = new ScheduleFilm();
        }
        return instance;
    }
}
