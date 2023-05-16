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
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class CinameNameAdapter extends ArrayAdapter<String> {
    private List<String> listCinemaName;
    private String filmName;
    public CinameNameAdapter(@NonNull Context context, int resource,  List<String> listCinemaName, String filmName) {
        super(context, resource, listCinemaName);
        this.filmName = filmName;
    }
    static  int selectedPosition = 0;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.cinema_booked_item, null);
        TextView cinemaName = (TextView) itemView.findViewById(R.id.cinemaName);
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.listTime);
        List<String> listTime = new ArrayList<String>();

        String item = getItem(position);

        FirebaseRequest.database.collection("showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot doc : listDocs){
                    //  Log.e("f",doc.get("NameCinema").toString() + "/" + item + "/" +doc.get("NameFilm").toString() + "/" + filmName);
                if(doc.get("NameCinema").equals(item) && doc.get("NameFilm").equals(filmName)){
                    listTime.add(doc.get("Time").toString());

//                        Log.e("f",doc.get("Time").toString() + doc.get("NameCinema")+ " " + item);
                 }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(new BookedActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new TimeBookedAdapter(listTime, null, item, itemView));
                cinemaName.setText(item);


            }
        });


        return itemView;
    }


}
