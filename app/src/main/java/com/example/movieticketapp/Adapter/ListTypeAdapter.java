package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;

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
    void loadListPost(String type){
        viewPager = activity.findViewById(R.id.typeMovieViewPage);
        List<FilmModel> listPosts = new ArrayList<FilmModel>();
        switch(type){
            case "All":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet1", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "Action":
                listPosts.add(new FilmModel(R.drawable.back_icon, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.control, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.avatar, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "Drama":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "Honor":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "War":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "Comedy":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;
            case "Crime":
                listPosts.add(new FilmModel(R.drawable.movie_poster, "Ralph Breaks the Internet", R.drawable.background_image,R.drawable.poster_image, 4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                listPosts.add(new FilmModel(R.drawable.movie_poster, "bìnhhh", R.drawable.background_image,R.drawable.poster_image,4.7, "Action & adventure, Comedy", "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph.\nRalph's goal was simple, wanting to win and get a medal to be considered a hero. But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex. Read more", "1h40"));
                break;



        }

        viewPager.setAdapter(new SliderAdapter(listPosts, viewPager));
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
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

