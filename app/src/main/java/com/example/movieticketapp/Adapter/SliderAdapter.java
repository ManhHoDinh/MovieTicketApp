package com.example.movieticketapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.PostItem;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.sliderViewHolder> {
    private List<PostItem> listPosts;
    private ViewPager2 viewPage;

    public SliderAdapter(List<PostItem> listPosts, ViewPager2 viewPage) {
        this.listPosts = listPosts;
        this.viewPage = viewPage;
    }

    @NonNull
    @Override
    public sliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
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
        void SetImage(PostItem postItem){
            imageView.setImageResource(postItem.getImage());
        }

    }
}