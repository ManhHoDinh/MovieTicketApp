package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewAllAdapter extends BaseAdapter {
    private Context context;
    private List<FilmModel> listFilm;

    public ViewAllAdapter(List<FilmModel> listFilm, Context context) {
        this.listFilm = listFilm;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listFilm.size();
    }

    @Override
    public Object getItem(int i) {
        return listFilm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_film_item, null);
            holder = new ViewHolder();
            holder.imageFilm = (ImageView) view.findViewById(R.id.imgFilm);
            holder.nameFilm = (TextView) view.findViewById(R.id.nameFilm);
            holder.item = (LinearLayout) view.findViewById(R.id.filmItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameFilm.setText(listFilm.get(i).getName());
        FilmModel f = listFilm.get(i);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, f);
                InforBooked.getInstance().film = f;
                view.getContext().startActivity(i);
            }
        });
        Picasso.get()
                .load(listFilm.get(i).getPosterImage())

                .into(holder.imageFilm);
        return view;

    }
    static class ViewHolder {

        ImageView imageFilm;
        TextView nameFilm;
        LinearLayout item;
    }
}
