package com.example.movieticketapp.Model;

import android.util.Log;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InforBooked {
    private static InforBooked instance;
    public Cinema cinema;
    public String dateBooked;
    public FilmModel film;
    public int height = 0;
    public List<Cinema> listCinema = new ArrayList<Cinema>();
    public boolean isDateSelected;
    public boolean isCitySelected;
    public boolean isTimeSelected;
    public String timeBooked;
    public int prevPosition = -1;
    public String typeFilm = "All";
    public int total = 0;
    public static InforBooked getInstance(){
        if(instance == null){
            instance = new InforBooked();
        }
        return  instance;
    }

    public void removeExpireFilm(){
        CollectionReference showtimeCollection= FirebaseRequest.database.collection("Showtime");
        FirebaseRequest.database.collection("Showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                Calendar calendar = Calendar.getInstance();
                for(DocumentSnapshot doc : listDocs){
                    Timestamp timestamp = doc.getTimestamp("TimeBooked");
                    if(calendar.getTime().after(timestamp.toDate())){

                        doc.getReference().delete();

                    }

                }

            }
        });
    }

}
