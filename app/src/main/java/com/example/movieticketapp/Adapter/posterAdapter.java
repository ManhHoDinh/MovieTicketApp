package com.example.movieticketapp.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.R;

import java.util.List;

public class posterAdapter extends RecyclerView.Adapter<posterAdapter.ViewHolder> {
    List<Integer> listPoster;

    public posterAdapter(List<Integer> listPoster) {
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
        holder.imageView.setImageResource(listPoster.get(position));
    }

    @Override
    public int getItemCount() {
        return listPoster.size();
    }
}
