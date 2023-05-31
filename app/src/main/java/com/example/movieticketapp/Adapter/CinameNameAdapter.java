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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CinameNameAdapter extends ArrayAdapter<String> {
    private List<String> listCinemaName;
    private String filmName;
    private LayoutInflater layoutInflater;

    public CinameNameAdapter(@NonNull Context context, int resource,  List<String> listCinemaName,String filmName) {
        super(context, resource, listCinemaName);
        this.filmName = filmName;
        this.listCinemaName = listCinemaName;
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
        InforBooked.getInstance().listCinemaName = listCinemaName;
        String item = getItem(position);

        try{
            if(Users.currentUser!=null)
                if(((Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                    if(ScheduleFilm.getInstance().isDateSelected && ScheduleFilm.getInstance().isCitySelected){
                        for (int i = 10; i <= 20;i++)
                            for (int j = 0; j <60; j=j+15)
                            {
                                listTime.add(i+":"+j);
                            }
                    }
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
                    layoutManager.setFlexDirection(FlexDirection.ROW);
                    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                    recyclerView.setLayoutManager(layoutManager);
                    FirebaseRequest.database.collection("showtime").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<DocumentSnapshot> listDocs = value.getDocuments();
                            List<ShowTime> listShowTime = new ArrayList<ShowTime>();
                            for(DocumentSnapshot doc : listDocs){
                                ShowTime showTime = doc.toObject(ShowTime.class);
                                listShowTime.add(showTime);
                      }
                            recyclerView.setAdapter(new TimeScheduleAdapter(listTime, null, filmName, item, itemView, null, null, listShowTime));

                        }
                    });

                    cinemaName.setText(item);
                }
            else {
                    Query query = FirebaseRequest.database.collection("showtime").orderBy("timeBooked");
                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(DocumentSnapshot doc : value){
                                Timestamp time = doc.getTimestamp("timeBooked");

                                DateFormat dateFormat = new SimpleDateFormat("EEE\nd");

                                if(doc.get("nameCinema").equals(item) && doc.get("nameFilm").equals(filmName) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                                    DateFormat timeFormat = new SimpleDateFormat("H:mm");
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
                            cinemaName.setText(item);
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
            FirebaseRequest.database.collection("showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : listDocs){
                        Timestamp time = doc.getTimestamp("TimeBooked");
                        DateFormat dateFormat = new SimpleDateFormat("EEE\nd");
                        if(doc.get("NameCinema").equals(item) && doc.get("NameFilm").equals(filmName) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                            DateFormat timeFormat = new SimpleDateFormat("H:mm");
                            listTime.add(timeFormat.format(time.toDate()));
                        }
                    }
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
                    layoutManager.setFlexDirection(FlexDirection.ROW);
                    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new TimeBookedAdapter(listTime, null,null, item, itemView, null, null));
                    cinemaName.setText(item);
                }
            });
        }
        return itemView;
    }
}
