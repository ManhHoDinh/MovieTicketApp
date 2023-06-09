package com.example.movieticketapp.Model;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFilm {
    private static ScheduleFilm instance;
    public String dateBooked;
    public String cinemaName;
    public boolean isDateSelected;
    public boolean isCitySelected;
    public List<ShowTime> listShowTime = new ArrayList<ShowTime>();

    public static ScheduleFilm getInstance() {
        if (instance == null) {
            instance = new ScheduleFilm();
        }
        return instance;
    }

}
