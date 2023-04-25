package com.example.movieticketapp.Adapter;

import android.content.Context;
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
import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class CinameNameAdapter extends ArrayAdapter<String> {
    private String[] listCinemaName;

    public CinameNameAdapter(@NonNull Context context, int resource, String[] listCinemaName) {
        super(context, resource, listCinemaName);
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
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        listTime.add("12:20");
        String item = getItem(position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(new BookedActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(new TimeBookedAdapter(listTime, null, item, itemView));

        recyclerView.setLayoutManager(linearLayoutManager);

        cinemaName.setText(item);
        return itemView;
    }



}
