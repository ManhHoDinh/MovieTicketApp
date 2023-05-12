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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.Model.FilmModel;
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

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ViewHolder>{
    private Activity activity;
    private String[] listType;
    private int checkedPosition = 0;
    private ViewPager2 viewPager;

    public ListTypeAdapter(Activity activity, String[] listType ) {
        this.activity = activity;
        this.listType = listType;
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
                    if(checkedPosition!=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
        void bind(String type){
            if(checkedPosition == getAdapterPosition()){
                loadListPost(type);
                typeBtn.setBackgroundColor(Color.TRANSPARENT);
                typeBtn.setBackground(ContextCompat.getDrawable(typeBtn.getContext(), R.drawable.background_button));
            }
            else {

                typeBtn.setBackgroundColor(Color.TRANSPARENT);
                typeBtn.setBackground(ContextCompat.getDrawable(typeBtn.getContext(), R.drawable.bg_tabview_button));
            }
        }
    }

    void addData( List<FilmModel> add,  FilmModel f)
    {
        add.add(f);
    }

    void loadListPost(String type){
        viewPager = activity.findViewById(R.id.typeMovieViewPage);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference MovieRef = db.collection("Movies");
        List<FilmModel> listPosts = new ArrayList<FilmModel>();;
        switch(type){
            case "All":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            listPosts.add(f);
                            Log.d(TAG, "Added data: " + f.getName());
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });

                break;
            case "Action":
                listPosts.clear();

                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("Action"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
               break;
            case "Drama":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("Drama"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
                break;
            case "Horror":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("Horror"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
                break;
            case "War":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("War"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
                break;
            case "Comedy":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("Comedy"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
                break;
            case "Crime":
                listPosts.clear();
                MovieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.d(TAG, "Listen failed", error);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : value)
                        {
                            FilmModel f = documentSnapshot.toObject(FilmModel.class);
                            if (f.getGenre().contains("Crime"))
                            {
                                listPosts.add(f);
                                Log.d(TAG, "Added data: " + f.getName());
                            }
                            else
                            {
                                Log.d(TAG, "Not added data: "+ f.getName());
                            }
                        }
                        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
                        viewPager.setClipToPadding(false);
                        viewPager.setClipChildren(false);
                        viewPager.setOffscreenPageLimit(3);
                    }
                });
               break;
        }


        //viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(24));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1- Math.abs(position);
                page.setScaleY(0.65f + r * 0.15f);
            }
        });
        viewPager.setPageTransformer(compositePageTransformer);
    }



    @NonNull
    @Override
    public ListTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        return new ListTypeAdapter.ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull ListTypeAdapter.ViewHolder holder, int position) {
        holder.typeBtn.setText(listType[position]);
        holder.bind(listType[position]);
    }



    @Override
    public int getItemCount() {
        return listType.length;
    }
}

