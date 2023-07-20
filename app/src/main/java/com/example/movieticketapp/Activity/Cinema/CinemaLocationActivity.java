package com.example.movieticketapp.Activity.Cinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;

public class CinemaLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    FusedLocationProviderClient client;

    GoogleMap ggmap;
    SupportMapFragment supportMapFragment;
    TextView nameCinema;
    String location;
    TextView addressCinema;
    Button backBtn;
    LinearLayout navigate;
    Address address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_location);
        nameCinema = findViewById(R.id.nameCinema);
        backBtn = findViewById(R.id.backbutton);
        addressCinema = findViewById(R.id.address);
        navigate = findViewById(R.id.navigateMap);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Cinema cinema = intent.getParcelableExtra("cinema");
        location = cinema.getName();

        nameCinema.setText(cinema.getName());
        addressCinema.setText(cinema.getAddress());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:" + address.getLatitude() + "," + address.getLongitude() + "?z=20");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                i.setPackage("com.google.android.apps.maps");
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
//        searchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = searchLocation.getQuery().toString();
//                List<Address> listAddress = null;
//                if(location != null || location.equals("")){
//                    Geocoder geocoder = new Geocoder(CinemaLocationActivity.this);
//                    try{
//                        listAddress = geocoder.getFromLocationName(location, 1);
//                    }
//                    catch (IOException e){
//                        e.printStackTrace();
//                    }
//                        Address address = listAddress.get(0);
//                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                        ggmap.addMarker(new MarkerOptions().position(latLng).title(location));
//                        ggmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        ggmap = googleMap;

        try {
            List<Address> listAddress = null;
            if(location != null || location.equals("")) {
                Log.e("s", "yes");
                Geocoder geocoder = new Geocoder(CinemaLocationActivity.this);
                try {
                    listAddress = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address = listAddress.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                ggmap.addMarker(new MarkerOptions().position(latLng).title(location));
                ggmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        } catch (Exception e){
            Toast.makeText(this, "Don't find the location!", Toast.LENGTH_SHORT).show();
        }


    }



}