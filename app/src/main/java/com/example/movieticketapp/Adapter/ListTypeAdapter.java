package com.example.movieticketapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.PostItem;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityMainBinding;

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

                    typeBtn.setBackgroundTintList(ColorStateList.valueOf(R.drawable.second_gradient));

                    loadListPost();
                    if(checkedPosition!=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
        void bind(){
            if(checkedPosition == getAdapterPosition()){
                loadListPost();
                typeBtn.setBackgroundTintList(ColorStateList.valueOf(R.drawable.second_gradient));
            }
            else {
                typeBtn.setBackgroundTintList(ColorStateList.valueOf(R.color.dark_background_2));

            }
        }


    }
    void loadListPost(){
        viewPager = activity.findViewById(R.id.typeMovieViewPage);List<PostItem> listPosts = new ArrayList<PostItem>();
        listPosts.add(new PostItem(R.drawable.movie_poster, "bìnhhhhh"));
        listPosts.add(new PostItem(R.drawable.movie_poster, "bìnhhh"));
        listPosts.add(new PostItem(R.drawable.movie_poster, "bìnhhh"));
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
        holder.bind();
    }



    @Override
    public int getItemCount() {
        return listType.length;
    }
}

