package com.example.movieticketapp.Adapter;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//public class FilmReportAdapter extends ArrayAdapter<FilmModel>{
//    List<FilmModel> Films;
//    FirebaseFirestore firestore;
//    List<Cinema> cinemas= new ArrayList<>();
//    String CinemaName;
//    int Month;
//    int Year;
//    ArrayList<BarEntry> listEntry;
//    public FilmReportAdapter(@NonNull Context context, int resource, List<FilmModel> Films, String CinemaName, int Month, int Year) {
//        super(context, resource, Films);
//        this.CinemaName = CinemaName;
//        this.Month = Month;
//        this.Year = Year;
//        this.Films = Films;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View itemView;
//        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_report_item, null);
//        firestore = FirebaseFirestore.getInstance();
//        ImageView Poster = itemView.findViewById(R.id.PosterImage);
//        TextView IndexTV = itemView.findViewById(R.id.IndexTV);
//        TextView FilmName = itemView.findViewById(R.id.filmNameTV);
//        TextView TotalTicket = itemView.findViewById(R.id.TotalTicketTV);
//        TextView TotalPrice = itemView.findViewById(R.id.TotalPriceTV);
//        ConstraintLayout filmItem = itemView.findViewById(R.id.filmItem);
//        IndexTV.setText(String.valueOf(position+1));
//        FilmModel film = getItem(position);
//        Picasso.get().load(film.getPosterImage()).into(Poster);
//        FilmName.setText(film.getName());
//        GetTotalTick(TotalTicket,TotalPrice, film);
//        filmItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(itemView.getContext(), InformationFilmActivity.class);
//                i.putExtra(ExtraIntent.film, film);
//                itemView.getContext().startActivity(i);
//            }
//        });
//    return itemView;
//
//    }
//        void GetTotalTick(TextView ticketTV,TextView totalTV, FilmModel film) {
//        final int[] ticket = {0};
//        final int[] totalPrice = {0};
//
//        firestore.collection("Ticket").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    return;
//                }
//
//                for (QueryDocumentSnapshot documentSnapshot : value) {
//                    Ticket s = documentSnapshot.toObject(Ticket.class);
//                    if ((s.getFilmID()).equals(film.getId())) {
//                        String seat = s.getSeat();
//                        int count = 1;
//                        count += seat.length() - s.getSeat().replace(",", "").length();
//                        //Initialize your Date however you like it.
//                        Date date = s.getTime().toDate();
//                        Calendar calendar = new GregorianCalendar();
//                        calendar.setTime(date);
//                        int TicketYear = calendar.get(Calendar.YEAR);
//                        int TicketMonth = calendar.get(Calendar.MONTH) + 1;
//
//                        for (int i = 0; i < cinemas.size(); i++)
//                            if (cinemas.get(i).getCinemaID().equals(s.getCinemaID())) {
//                                if(Month==0)
//                                {
//                                    if(Year == 0)
//                                    {
//                                        ticket[0] += count;
//                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
//                                    }
//                                    else if(TicketYear==Year)
//                                    {
//                                        ticket[0] += count;
//                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
//                                    }
//                                }
//                                else if(TicketMonth==Month)
//                                {
//                                    if(Year == 0)
//                                    {
//                                        ticket[0] += count;
//                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
//                                    }
//                                    else if(TicketYear==Year)
//                                    {
//                                        ticket[0] += count;
//                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
//                                    }
//                                }
//
//                            }
//                    }
//                }
//                NumberFormat numberFormat = new DecimalFormat("#,###");
//                ticketTV.setText("Ticket: " + numberFormat.format(ticket[0]));
//                totalTV.setText("Revenue: " + numberFormat.format(totalPrice[0]));
////                TotalPrice+= totalPrice[0];
////                if (onDataChangedListener != null) {
////                    onDataChangedListener.onDataChanged(TotalPrice);
////                }
//            }
//        });
//    }
//
//}
public class FilmReportAdapter extends RecyclerView.Adapter<FilmReportAdapter.ViewHolder> {
    FirebaseFirestore firestore;
    List<Cinema> cinemas= new ArrayList<>();
    String CinemaName;
    int Month;
    int Year;
    ArrayList<BarEntry> listEntry;

    @Override
    public void onBindViewHolder(@NonNull FilmReportAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.IndexTV.setText(String.valueOf(position+1));
        Picasso.get().load(Films.get(position).getPosterImage()).into(holder.Poster);
        holder.FilmName.setText(Films.get(position).getName());
        GetTotalTick(holder.TotalTicket,holder.TotalPrice, Films.get(position));
        holder.filmItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, Films.get(position));
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    List<FilmModel> Films;

    public FilmReportAdapter(List<FilmModel> Films, String CinemaName, int Month, int Year) {
        this.Films = Films;
        firestore = FirebaseFirestore.getInstance();
        this.CinemaName = CinemaName;
        this.Month = Month;
        this.Year=Year;
        firestore.collection("Cinema").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot : value) {
                    Cinema c = documentSnapshot.toObject(Cinema.class);
                    if(CinemaName.equals("All Cinema"))
                        cinemas.add(c);
                    else if(c.getName().equals(CinemaName))
                        cinemas.add(c);
                }
            }
        }); }

    @Override
    public int getItemCount() {
        return Films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView IndexTV;
        ImageView Poster;
        TextView FilmName;
        TextView TotalTicket;
        TextView TotalPrice;
        ConstraintLayout filmItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // imageView = itemView.findViewById(R.id.posterItem);
            Poster = itemView.findViewById(R.id.PosterImage);
            IndexTV = itemView.findViewById(R.id.IndexTV);
            FilmName = itemView.findViewById(R.id.filmNameTV);
            TotalTicket = itemView.findViewById(R.id.TotalTicketTV);
            TotalPrice = itemView.findViewById(R.id.TotalPriceTV);
            filmItem= itemView.findViewById(R.id.filmItem);
        }
    }

    @NonNull
    @Override
    public FilmReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_report_item, parent, false);
        return new FilmReportAdapter.ViewHolder(itemView);
    }
//    public interface OnDataChangedListener {
//        void onDataChanged(int totalPrice);
//    }

//    private OnDataChangedListener onDataChangedListener;
//
//    public void setOnDataChangedListener(OnDataChangedListener listener) {
//        this.onDataChangedListener = listener;
//    }

//    public int getTotalPrice() {
//        return TotalPrice;
//    }

    void GetTotalTick(TextView ticketTV,TextView totalTV, FilmModel film) {
        final int[] ticket = {0};
        final int[] totalPrice = {0};

        firestore.collection("Ticket").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot : value) {
                    Ticket s = documentSnapshot.toObject(Ticket.class);
                    if ((s.getFilmID()).equals(film.getId())) {
                        String seat = s.getSeat();
                        int count = 1;
                        count += seat.length() - s.getSeat().replace(",", "").length();
                        //Initialize your Date however you like it.
                        Date date = s.getTime().toDate();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date);
                        int TicketYear = calendar.get(Calendar.YEAR);
                        int TicketMonth = calendar.get(Calendar.MONTH) + 1;

                        for (int i = 0; i < cinemas.size(); i++)
                            if (cinemas.get(i).getCinemaID().equals(s.getCinemaID())) {
                                if(Month==0)
                                {
                                    if(Year == 0)
                                    {
                                        ticket[0] += count;
                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
                                    }
                                    else if(TicketYear==Year)
                                    {
                                        ticket[0] += count;
                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
                                    }
                                }
                                else if(TicketMonth==Month)
                                {
                                    if(Year == 0)
                                    {
                                        ticket[0] += count;
                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
                                    }
                                    else if(TicketYear==Year)
                                    {
                                        ticket[0] += count;
                                        totalPrice[0] += cinemas.get(i).getPrice() * count;
                                    }
                                }

                            }
                    }
                }
                NumberFormat numberFormat = new DecimalFormat("#,###");
                ticketTV.setText("Ticket: " + numberFormat.format(ticket[0]));
                totalTV.setText("Revenue: " + numberFormat.format(totalPrice[0]));
//                TotalPrice+= totalPrice[0];
//                if (onDataChangedListener != null) {
//                    onDataChangedListener.onDataChanged(TotalPrice);
//                }
            }
        });
    }

}
