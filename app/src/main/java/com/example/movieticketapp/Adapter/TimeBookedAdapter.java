package com.example.movieticketapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.ScheduleFilm;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TimeBookedAdapter extends RecyclerView.Adapter<TimeBookedAdapter.ViewHolder> {
    private List<String> listDate;
    private List<String> listTime;
    private Cinema cinema;
    private Button preButton;
    private int checkedPosition = -1;
    private static String prevType = "";

    private static int prevPosition = -1;
    private View timeView;
    private static View prevView;
    private ListView timelistView;
    private Activity activity;
    private FilmModel film;


    private List<Integer> listSelected = new ArrayList<Integer>();
    public TimeBookedAdapter(List<String> listDate, @Nullable List<String> listTime, @Nullable FilmModel film, @Nullable Cinema cinema, @Nullable View view, @Nullable ListView timelistView, @Nullable Activity activity) {
        this.listDate = listDate;
        this.listTime = listTime;
        this.cinema = cinema;
        this.timeView = view;
        this.timelistView = timelistView;
        this.activity = activity;
        this.film = film;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        Button dateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateBtn = (Button) itemView.findViewById(R.id.dateBtn);
            dateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(timeView != null){

                        if(listTime == null){
                            //if(prevType != cinema.getName()){
                                if(prevView != null){
                                    TextView tv = (TextView) prevView.findViewById(R.id.cinemaName);
                                    RecyclerView rv = (RecyclerView) prevView.findViewById(R.id.listTime);
                                    Log.e("f", tv.getText().toString());
                                    if(InforBooked.getInstance().prevPosition >-1){
                                        View v = rv.getLayoutManager().findViewByPosition(InforBooked.getInstance().prevPosition);
                                        Button btn  = v.findViewById(R.id.dateBtn);
                                        btn.setBackgroundColor(Color.TRANSPARENT);
                                        btn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.bg_tabview_button));
                                    }
                                }
                           // }
                            InforBooked.getInstance().isTimeSelected = true;
                            InforBooked.getInstance().timeBooked = dateBtn.getText().toString();
                            InforBooked.getInstance().cinema = cinema;
                        }
                        prevType = cinema.getName();
                        prevView = timeView;
                        InforBooked.getInstance().prevPosition = getAdapterPosition();

                    }

                    else InforBooked.getInstance().dateBooked = dateBtn.getText().toString();
                    if(film != null){
                        ScheduleFilm.getInstance().dateBooked = InforBooked.getInstance().dateBooked;
                        ScheduleFilm.getInstance().isDateSelected = true;
                    }

                    InforBooked.getInstance().isDateSelected = true;
                    dateBtn.setBackgroundColor(Color.TRANSPARENT);

                    dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.background_button));

                    if(checkedPosition!=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                    if(listTime != null){
                        List<String> listCinemaName = new ArrayList<String>();



                        FirebaseRequest.database.collection("Cinema").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();

                                if(!InforBooked.getInstance().dateBooked.equals(null)){

                                    CinameNameAdapter cinameNameAdapter = new CinameNameAdapter(activity, R.layout.cinema_booked_item,InforBooked.getInstance().listCinema, InforBooked.getInstance().film);
                                    timelistView.setAdapter(cinameNameAdapter);


                                   // Helper.getListViewSize(timelistView, activity);
                                }



                            }
                        });
                    }

                }
            });
        }

        void Binding(){
            if(checkedPosition != getAdapterPosition()){
                dateBtn.setBackgroundColor(Color.TRANSPARENT);
                dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.bg_tabview_button));
            }
            else {
                dateBtn.setBackgroundColor(Color.TRANSPARENT);
                dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.background_button));
            }


        }
    }
    @NonNull
    @Override
    public TimeBookedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_booked_item, parent, false);
        return new TimeBookedAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeBookedAdapter.ViewHolder holder, int position) {
        if(listTime != null){
            holder.dateBtn.setText(listDate.get(position) + "\n" + listTime.get(position));
            holder.Binding();

        }
        else{
            holder.dateBtn.setText(listDate.get(position));

            if(holder.dateBtn.getText().toString().equals(InforBooked.getInstance().timeBooked) && cinema.getName().equals(prevType) ){
                holder.dateBtn.setBackgroundColor(Color.TRANSPARENT);
                holder.dateBtn.setBackground(ContextCompat.getDrawable(holder.dateBtn.getContext(), R.drawable.background_button));
            }
            else holder.Binding();

        }


    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }


}
