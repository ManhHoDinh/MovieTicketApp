package com.example.movieticketapp.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Booking.ShowTimeScheduleActivity;
import com.example.movieticketapp.Activity.Cinema.CinemaLocationActivity;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CinameNameAdapter extends ArrayAdapter<Cinema> {

    private FilmModel film;
    private LayoutInflater layoutInflater;
    private List<Cinema> listCinema;
    private Context context;
    FusedLocationProviderClient client;
   TextView distance;





    public CinameNameAdapter(@NonNull Context context, int resource, List<Cinema> listCinema, FilmModel film) {
        super(context, resource, listCinema);
        this.film = film;
        this.listCinema = listCinema;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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
        LinearLayout locationLayout = itemView.findViewById(R.id.locationCinema);
        TextView addressCinema = itemView.findViewById(R.id.addressCinema);
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.listTime);
        distance = itemView.findViewById(R.id.distance);
        ImageView showHideBtn = itemView.findViewById(R.id.showHideBtn);
        LinearLayout showHideLayout = itemView.findViewById(R.id.showHideLayout);
        ConstraintLayout cinemaCl = itemView.findViewById(R.id.cinemaCl);
        List<String> listTime = new ArrayList<String>();

        InforBooked.getInstance().listCinema = listCinema;
        Cinema item = getItem(position);
        client = LocationServices.getFusedLocationProviderClient(context);

        if((InforBooked.getInstance().isCitySelected && InforBooked.getInstance().isDateSelected) || (ScheduleFilm.getInstance().isCitySelected && ScheduleFilm.getInstance().isDateSelected)) {
            distance.setVisibility(View.VISIBLE);
            showHideBtn.setVisibility(View.VISIBLE);
        }
        else {
            distance.setVisibility(View.GONE);
            showHideBtn.setVisibility(View.GONE);
        }
        try{
            List<Address> listAddress = null;
            Geocoder geocoder = new Geocoder(context);

            try {
                listAddress = geocoder.getFromLocationName(item.getName(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = listAddress.get(0);
            Dexter.withContext(context.getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            getMyLocation(address, distance);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            if(permissionDeniedResponse.isPermanentlyDenied()){
                                distance.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();
        } catch (Exception e){
            distance.setVisibility(View.GONE);
            locationLayout.setVisibility(View.GONE);

        }
        finally {
            try{
                if(Users.currentUser!=null)
                    if(((Users.currentUser.getAccountType().toString()).equals("admin")))
                    {
                        if(ScheduleFilm.getInstance().isDateSelected && ScheduleFilm.getInstance().isCitySelected){
                            for (int i = 10; i <= 20;i++){
                                for (int j = 0; j <60; j=j+15)
                                {
                                    NumberFormat formatter = new DecimalFormat("00");

                                    listTime.add(formatter.format(i)+":" + formatter.format(j));
                                }
                            }
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext()){
                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }

                                @Override
                                public boolean canScrollHorizontally() {
                                    return false;
                                }

                            };
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
                            addressCinema.setText(item.getAddress());
                        }


                    }
                    else {
                        Query query = FirebaseRequest.database.collection("Showtime").orderBy("timeBooked");

                        if(InforBooked.getInstance().isDateSelected && InforBooked.getInstance().isCitySelected){

                            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for(DocumentSnapshot doc : value){
                                        Timestamp time = doc.getTimestamp("timeBooked");

                                        DateFormat dateFormat = new SimpleDateFormat("EEE\nd", Locale.ENGLISH);

                                        if(doc.get("cinemaID").equals(item.getCinemaID()) && doc.get("filmID").equals(film.getId()) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                                            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                                            listTime.add(timeFormat.format(time.toDate()));
                                        }
                                    }
                                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext())
                                    {
                                        @Override
                                        public boolean canScrollVertically() {
                                            return false;
                                        }

                                        @Override
                                        public boolean canScrollHorizontally() {
                                            return false;
                                        }

                                    };
                                    layoutManager.setFlexDirection(FlexDirection.ROW);
                                    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                                    recyclerView.setLayoutManager(layoutManager);
                                    if(!InforBooked.getInstance().isCitySelected || !InforBooked.getInstance().isDateSelected){
                                        recyclerView.setAdapter(new TimeBookedAdapter(new ArrayList<String>(), null,null, item, itemView, null, null));
                                    }
                                    else recyclerView.setAdapter(new TimeBookedAdapter(listTime, null,null, item, itemView, null, null));
                                    cinemaName.setText(item.getName());
                                    addressCinema.setText(item.getAddress());
                                    if(listTime.size() > 0){
                                        showHideBtn.setImageResource(R.drawable.arrow_up);
                                        showHideBtn.setTag("show");
                                        showHideLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                        }
                    }
                locationLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Address> listAddress = new ArrayList<>();
                        Intent intent = new Intent(context, CinemaLocationActivity.class);
                        intent.putExtra("cinema", item);
                        context.startActivity(intent);
                    }
                });
                cinemaCl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(showHideBtn.getTag().equals("hide")){
                            showHideBtn.setImageResource(R.drawable.arrow_up);
                            showHideBtn.setTag("show");
                            showHideLayout.setVisibility(View.VISIBLE);


                        }
                        else {
                            showHideBtn.setImageResource(R.drawable.arrow_down);
                            showHideBtn.setTag("hide");
                            showHideLayout.setVisibility(View.GONE);
                        }
                    }
                });


            }
            catch (Exception e)
            {
                FirebaseRequest.database.collection("Showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc : listDocs){
                            Timestamp time = doc.getTimestamp("timeBooked");
                            DateFormat dateFormat = new SimpleDateFormat("EEE\nd", Locale.ENGLISH);
                            if(doc.get("cinemaID").equals(item.getCinemaID()) && doc.get("filmID").equals(film.getId()) && dateFormat.format(time.toDate()).equals(InforBooked.getInstance().dateBooked)){
                                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
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
        }

        return itemView;
    }
    public void getMyLocation(Address address, TextView distance) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("Hello", "Hello");

            return;
        }
        Log.d("Hello", "Hello");

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                        float[] results = new float[10];
                        LatLng source = new LatLng(location.getLatitude(), location.getLongitude());
                Log.e( String.valueOf(location.getLatitude()), location.getLongitude()+"LocationA");
                        LatLng destination = new LatLng(address.getLatitude(), address.getLongitude());
                        Location.distanceBetween(source.latitude, source.longitude, destination.latitude, destination.longitude, results);
                        distance.setText(String.format("%.1f", results[0]/1000) + " km");
            }
        });
    }

}
