package com.example.movieticketapp.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.grpc.internal.LogExceptionRunnable;

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ViewHolder> {
    private Activity activity;
    private String[] listType;
    private int checkedPosition = 0;
    private ViewPager2 NowPlaying;
    private RecyclerView Expired;

    private  RecyclerView ComingSoon;
    private List<FilmModel> PlayingFilms;
    private List<FilmModel> ComingFilms;
    private List<FilmModel> ExpiredFilms;

    public ListTypeAdapter(Activity activity, String[] listType) {
        this.activity = activity;
        this.listType = listType;
        this.PlayingFilms = new ArrayList<FilmModel>();
        this.ComingFilms = new ArrayList<FilmModel>();
        this.ExpiredFilms= new ArrayList<FilmModel>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button typeBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            typeBtn = (Button) itemView.findViewById(R.id.typeItem);
            typeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeBtn.setBackgroundColor(Color.TRANSPARENT);
                    typeBtn.setBackground(ContextCompat.getDrawable(typeBtn.getContext(), R.drawable.background_button));
                    loadListPost(typeBtn.getText().toString());
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                    InforBooked.getInstance().typeFilm = typeBtn.getText().toString();
                }
            });
        }

        void bind(String type) {
            if (checkedPosition == getAdapterPosition()) {
                loadListPost(type);
                typeBtn.setBackgroundColor(Color.TRANSPARENT);
                typeBtn.setBackground(ContextCompat.getDrawable(typeBtn.getContext(), R.drawable.background_button));
            } else {
                typeBtn.setBackgroundColor(Color.TRANSPARENT);
                typeBtn.setBackground(ContextCompat.getDrawable(typeBtn.getContext(), R.drawable.bg_tabview_button));
            }
        }
    }

    void loadListPost(String type) {
        NowPlaying = activity.findViewById(R.id.typeMovieViewPage);
        ComingSoon = activity.findViewById(R.id.comingMovieView);
        Expired= activity.findViewById(R.id.ExpiredMovieView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference MovieRef = db.collection("Movies");

        if (type.equals("All")) {
            PlayingFilms.clear();
            ComingFilms.clear();
            ExpiredFilms.clear();
            MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        return;
                    }
                    PlayingFilms.clear();
                    ComingFilms.clear();
                    ExpiredFilms.clear();
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        FilmModel f = documentSnapshot.toObject(FilmModel.class);
                        try{
                            if(f.getMovieBeginDate().toDate().before(Helper.getCurrentDate()))
                                PlayingFilms.add(f);
                            else
                                ComingFilms.add(f);

                            if (f.getMovieEndDate().toDate().before(Helper.getCurrentDate()))
                            {
                                PlayingFilms.remove(f);
                                ComingFilms.remove(f);
                                ExpiredFilms.add(f);
                            }
                        }
                        catch (Exception e)
                        {
                            Log.d(e.getMessage(),e.getMessage());
                        }
                    }
                    updateViewPager();
                }
            });
        } else {
            PlayingFilms.clear();
            ComingFilms.clear();
            ExpiredFilms.clear();
            MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        return;
                    }
                    PlayingFilms.clear();
                    ComingFilms.clear();
                    ExpiredFilms.clear();
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        FilmModel f = documentSnapshot.toObject(FilmModel.class);
                        if (f.getGenre().contains(type)) {
                            try{
                                if(f.getMovieBeginDate().toDate().before(Helper.getCurrentDate()))
                                    PlayingFilms.add(f);
                                else
                                    ComingFilms.add(f);

                                if (f.getMovieEndDate().toDate().before(Helper.getCurrentDate()))
                                {
                                    PlayingFilms.remove(f);
                                    ComingFilms.remove(f);
                                    ExpiredFilms.add(f);
                                }
                            }
                            catch (Exception e)
                            {
                                Log.d(e.getMessage(),e.getMessage());
                            }
                        } else {
                        }
                    }
                    updateViewPager();
                }
            });
        }
    }

    void updateViewPager() {
        SliderAdapter sliderAdapter = new SliderAdapter(PlayingFilms, NowPlaying);
        NowPlaying.setAdapter(sliderAdapter);
        NowPlaying.setClipToPadding(false);
        NowPlaying.setClipChildren(false);
        NowPlaying.setOffscreenPageLimit(3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        ComingSoon.setAdapter(new posterAdapter(ComingFilms));
        ComingSoon.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        Expired.setAdapter(new posterAdapter(ExpiredFilms));
        Expired.setLayoutManager(linearLayoutManager2);
        // Other transformations and settings
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(24));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.65f + r * 0.15f);
            }
        });
        NowPlaying.setPageTransformer(compositePageTransformer);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.typeBtn.setText(listType[position]);
        holder.bind(listType[position]);
    }

    @Override
    public int getItemCount() {
        return listType.length;
    }
}