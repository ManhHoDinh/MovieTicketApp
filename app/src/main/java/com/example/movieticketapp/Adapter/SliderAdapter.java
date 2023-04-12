package com.example.movieticketapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.Activity.InformationFilmActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.sliderViewHolder> {
    private List<FilmModel> listPosts;
    private ViewPager2 viewPage;

    public SliderAdapter(List<FilmModel> listPosts, ViewPager2 viewPage) {
        this.listPosts = listPosts;
        this.viewPage = viewPage;
    }

    @NonNull
    @Override
    public sliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        FilmModel f = listPosts.get(viewType);
        itemView.findViewById(R.id.sliderItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, f);
                parent.getContext().startActivity(i);
            }
        });
        return new sliderViewHolder(itemView);


    }
    @Override
    public void onBindViewHolder(@NonNull sliderViewHolder holder, int position) {
        holder.textView.setText(listPosts.get(position).getName());
        holder.SetImage(listPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    class sliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView textView;

        public sliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.postSlider);
            textView = (TextView) itemView.findViewById(R.id.namePost);

        }
        void SetImage(FilmModel postItem){
            imageView.setImageResource(postItem.getPrimaryImage());
        }

    }
}