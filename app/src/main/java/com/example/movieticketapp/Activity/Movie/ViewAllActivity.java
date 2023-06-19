package com.example.movieticketapp.Activity.Movie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.ViewAllAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    GridView filmGridview;
    Button backBtn;
    TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        filmGridview = findViewById(R.id.filmGridView);
        backBtn = findViewById(R.id.backbutton);
        title = findViewById(R.id.titleViewAll);
       List<FilmModel> listFilm = new ArrayList<FilmModel>();
       Intent intent = getIntent();
       String status = intent.getStringExtra("status");
       if(status.equals("playing")){
           title.setText("Now Playing");
       }
       else {
           title.setText("Coming Soon");
       }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        checkAccountType();
        FirebaseRequest.database.collection("Movies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                listFilm.clear();
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    FilmModel f = documentSnapshot.toObject(FilmModel.class);
                    if(status.equals("playing")){
                        if(f.getMovieBeginDate().toDate().before(currentDate)){
                            if(InforBooked.getInstance().typeFilm.equals("All")){
                                listFilm.add(f);
                            }
                            else if (f.getGenre().contains(InforBooked.getInstance().typeFilm)) {
                                listFilm.add(f);
                            } else {
                            }
                        }
                    }
                    else {
                        if(f.getMovieBeginDate().toDate().after(currentDate)){
                            if(InforBooked.getInstance().typeFilm.equals("All")){
                                listFilm.add(f);
                            }
                            else if (f.getGenre().contains(InforBooked.getInstance().typeFilm)) {
                                listFilm.add(f);
                            } else {
                            }
                        }

                    }


                }
                filmGridview.setAdapter(new ViewAllAdapter(listFilm, ViewAllActivity.this));
            }
        });


    }
    void checkAccountType()
    {
        ImageView addMovie= findViewById(R.id.AddMovie);

        try{
            Log.d("account type", Users.currentUser.getAccountType());
            if(Users.currentUser!=null)
                if((!(Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                    ViewGroup.LayoutParams params = addMovie.getLayoutParams();
                    params.height = 0;
                    addMovie.setLayoutParams(params);
                    addMovie.setVisibility(View.INVISIBLE);
                }
            else {
                    addMovie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(ViewAllActivity.this, AddMovieActivity.class);
                            startActivity(i);
                        }
                    });
                }
        }
        catch (Exception e)
        {
            ViewGroup.LayoutParams params = addMovie.getLayoutParams();
            params.height = 0;
            addMovie.setLayoutParams(params);
            addMovie.setVisibility(View.INVISIBLE);
        }
    }
}