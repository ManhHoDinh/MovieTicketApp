package com.example.movieticketapp.Adapter;



import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class posterAdapter extends RecyclerView.Adapter<posterAdapter.ViewHolder> {
    List<FilmModel> listPoster;

    public posterAdapter(List<FilmModel> listPoster) {
        this.listPoster = listPoster;
      }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterItem);
        }
    }
    @NonNull
    @Override
    public posterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent, false);
        return new posterAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull posterAdapter.ViewHolder holder, int position) {
        Picasso.get().load(listPoster.get(position).getPosterImage()).into(holder.imageView);
        FilmModel f =listPoster.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.imageView.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, f);
                InforBooked.getInstance().film = f;
                holder.imageView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPoster.size();
    }
}
