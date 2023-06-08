package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Booking.ShowTimeScheduleActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.ScheduleFilm;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CinameNameAdapter extends ArrayAdapter<Cinema> {

    private FilmModel film;
    private LayoutInflater layoutInflater;
    private List<Cinema> listCinema;

    public CinameNameAdapter(@NonNull Context context, int resource, List<Cinema> listCinema, FilmModel film) {
        super(context, resource, listCinema);
        this.film = film;
        this.listCinema = listCinema;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    static  int selectedPosition = 0;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;

        if(convertView == null){
            itemView = layoutInflater.inflate(R.layout.cinema_booked_item, null);
            convertView = layoutInflater.inflate(R.layout.cinema_booked_item, null);
        }
        else  itemView = convertView;

        TextView cinemaName = (TextView) itemView.findViewById(R.id.cinemaName);
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.listTime);
        List<String> listTime = new ArrayList<String>();
        InforBooked.getInstance().listCinema = listCinema;
        Cinema item = getItem(position);
        Log.e("fd", item.getName());

        try{
            if(Users.currentUser!=null)
                if(((Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                    if(ScheduleFilm.getInstance().isDateSelected && ScheduleFilm.getInstance().isCitySelected){
                        for (int i = 10; i <= 20;i++)
                            for (int j = 0; j <60; j=j+15)
                            {
                                NumberFormat formatter = new DecimalFormat("00");

                                listTime.add(formatter.format(i)+":" + formatter.format(j));
                            }
                    }
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
                    layoutManager.setFlexDirection(FlexDirection.ROW);
                    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                    recyclerView.setLayoutManager(layoutManager);
                    FirebaseRequest.database.collection("Showtime").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<DocumentSnapshot> listDocs = value.getDocuments();
                            List<ShowTime> listShowTime = new ArrayList<ShowTime>();
                            for(DocumentSnapshot doc : listDocs){
                                ShowTime showTime = doc.toObject(ShowTime.class);
                                listShowTime.add(showTime);
                      }
                            recyclerView.setAdapter(new TimeScheduleAdapter(listTime, null, film, item, itemView, null, null, listShowTime));

                        }
                    });

                    cinemaName.setText(item.getName());
                }
            else {
                    Query query = FirebaseRequest.database.collection("Showtime").orderBy("timeBooked");
                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(DocumentSnapshot doc : value){
                                Timestamp time = doc.getTimestamp("timeBooked");

                                DateFormat dateFormat = new SimpleDateFormat("EEE\nd");

                                if(doc.get("cinemaID").equals(item.getCinemaID()) && doc.get("filmID").equals(film.getId()) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                                    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                    listTime.add(timeFormat.format(time.toDate()));
                                }
                            }
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
                            layoutManager.setFlexDirection(FlexDirection.ROW);
                            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                            recyclerView.setLayoutManager(layoutManager);
                            if(!InforBooked.getInstance().isCitySelected || !InforBooked.getInstance().isDateSelected){
                                recyclerView.setAdapter(new TimeBookedAdapter(new ArrayList<String>(), null,null, item, itemView, null, null));
                            }
                            else recyclerView.setAdapter(new TimeBookedAdapter(listTime, null,null, item, itemView, null, null));
                            cinemaName.setText(item.getName());
                        }
                    });
//                   query.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
//
//                            for(DocumentSnapshot doc : listDocs){
//                                Timestamp time = doc.getTimestamp("timeBooked");
//
//                                DateFormat dateFormat = new SimpleDateFormat("EEE\ndd");
//
//                                if(doc.get("nameCinema").equals(item) && doc.get("nameFilm").equals(filmName) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
//                                    DateFormat timeFormat = new SimpleDateFormat("H:mm");
//                                    listTime.add(timeFormat.format(time.toDate()));
//                                }
//                            }
//                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
//                            layoutManager.setFlexDirection(FlexDirection.ROW);
//                            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
//                            recyclerView.setLayoutManager(layoutManager);
//                            if(InforBooked.getInstance().dateBooked == null){
//                                recyclerView.setAdapter(new TimeBookedAdapter(new ArrayList<String>(), null,null, item, itemView, null, null));
//                            }
//                            else recyclerView.setAdapter(new TimeBookedAdapter(listTime, null,null, item, itemView, null, null));
//                            cinemaName.setText(item);
//                        }
//                    });


                }
        }
        catch (Exception e)
        {
            FirebaseRequest.database.collection("Showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : listDocs){
                        Timestamp time = doc.getTimestamp("timeBooked");
                        DateFormat dateFormat = new SimpleDateFormat("EEE\nd");
                        if(doc.get("cinemaID").equals(item.getCinemaID()) && doc.get("filmID").equals(film.getId()) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            listTime.add(timeFormat.format(time.toDate()));
                        }
                    }
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
                    layoutManager.setFlexDirection(FlexDirection.ROW);
                    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new TimeBookedAdapter(listTime, null,null, item, itemView, null, null));
                    cinemaName.setText(item.getName());
                }
            });
        }
        return itemView;
    }
}
