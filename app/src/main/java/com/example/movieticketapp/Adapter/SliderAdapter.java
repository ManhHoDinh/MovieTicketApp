package com.example.movieticketapp.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.sliderViewHolder> {
    private List<FilmModel> listPosts;

    private ViewPager2 viewPage;

    public SliderAdapter(List<FilmModel> listPosts, ViewPager2 viewPage) {
        this.listPosts = listPosts;
        this.viewPage = viewPage;
    }
    int i = 0;
    @NonNull
    @Override
    public sliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new sliderViewHolder(itemView, i);
    }


    @Override
    public void onBindViewHolder(@NonNull sliderViewHolder holder, int position) {
        holder.textView.setText(listPosts.get(position).getName());
        holder.SetImage(listPosts.get(position));
        DecimalFormat df = new DecimalFormat("0.0");
        float vote = listPosts.get(position).getVote();

        holder.rating.setRating(vote);
        holder.ratingPoint.setText(df.format(vote)+"");
        FilmModel f =listPosts.get(position);
        Log.e("ddf", f.getBackGroundImage());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Log.e("fd", position + "f");
                Intent i = new Intent(holder.view.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, f);
                InforBooked.getInstance().film= f;
                holder.view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    class sliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView textView;
        private RatingBar rating;
        private TextView ratingPoint;
        private  View view;
        /////ADD Film TO Film Information
        public sliderViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.postSlider);
            textView = (TextView) itemView.findViewById(R.id.namePost);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            ratingPoint = (TextView) itemView.findViewById(R.id.ratingPoint);
            view = itemView;
            FilmModel f =listPosts.get(position);
            i++;

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.e("fd", listPosts.size()+"");
//                    Intent i = new Intent(itemView.getContext(), InformationFilmActivity.class);
//                    i.putExtra(ExtraIntent.film, f);
//                    InforBooked.getInstance().film= f;
//                    itemView.getContext().startActivity(i);
//                }
//            });
        }
        void SetImage(FilmModel postItem){
            Picasso.get()
                    .load(postItem.getPrimaryImage())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }

    }
}