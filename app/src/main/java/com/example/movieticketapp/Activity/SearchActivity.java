package com.example.movieticketapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.ListSearchAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
private androidx.appcompat.widget.SearchView searchView;
private RecyclerView listSearchResult;
private  List<FilmModel> listFilm;
private TextView closeBtn;
private ListSearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchField);
        listSearchResult = findViewById(R.id.listSearchResult);
        closeBtn = findViewById(R.id.closeBtn);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.requestFocusFromTouch();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
            }
        });
        listFilm = new ArrayList<FilmModel>();
        listSearchResult.setHasFixedSize(true);
        listSearchResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchView.clearFocus();
        adapter = new ListSearchAdapter(listFilm);
        listSearchResult.setAdapter(adapter);
        //addListFilm();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


    }

    private void filterList(String s) {
        listFilm = new ArrayList<>();
        List<FilmModel> listResult = new ArrayList<>();
        FirebaseRequest.database.collection("Movies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> listDocs = value.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    FilmModel film = doc.toObject(FilmModel.class);
                    listFilm.add(film);
                }


                for(FilmModel f : listFilm){
                    if(f.getName().toLowerCase().contains(s.toLowerCase()) && !s.equals("")){
                        listResult.add(f);
                    }
                }

                if(listResult.isEmpty()){
                    Toast.makeText(SearchActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

                    adapter.setFilterList(listResult);


            }
        });


    }
    void addListFilm(){
        FirebaseRequest.database.collection("Movies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> listDocs = value.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    FilmModel film = doc.toObject(FilmModel.class);
                    listFilm.add(film);
                }
//                for(FilmModel f : listFilm){
//                    if(f.getName().toLowerCase().contains(s.toLowerCase()) || !s.equals("")){
//                        listResult.add(f);
//                    }
//                }
//
//
//                if(listResult.isEmpty()){
//                    Toast.makeText(SearchActivity.this, "No data found", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    adapter.setFilterList(listResult);
//
//                }
            }
        });
    }
}