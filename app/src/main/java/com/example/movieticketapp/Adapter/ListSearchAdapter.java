package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.Timestamp;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ItemViewHolder> {
    private List<FilmModel> listSearch;
    private Context context;

    public ListSearchAdapter(List<FilmModel> listSearch, Context context) {
        this.listSearch = listSearch;
        this.context = context;
    }
    public void setFilterList(List<FilmModel> filterList){
        this.listSearch = filterList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListSearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new ListSearchAdapter.ItemViewHolder(view);
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView imageFilm;
        TextView nameFilm;
        TextView timeShow;
        TextView status;
        Button inforBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFilm = itemView.findViewById(R.id.imgSearchResult);
            nameFilm = itemView.findViewById(R.id.nameFilm);
            timeShow = itemView.findViewById(R.id.timeShow);
            status = itemView.findViewById(R.id.filmStatus);
            inforBtn = itemView.findViewById(R.id.inforBtn);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListSearchAdapter.ItemViewHolder holder, int position) {
        FilmModel filmModel = listSearch.get(position);
        holder.timeShow.setText(filmModel.getDurationTime());
        Picasso.get().load(filmModel.getPosterImage()).into(holder.imageFilm);
        holder.nameFilm.setText(filmModel.getName());
        Timestamp movieBeginDate = filmModel.getMovieBeginDate();
        ConstraintLayout layoutElement = holder.itemView.findViewById(R.id.SearchFilmLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        if(movieBeginDate.toDate().before(Helper.getCurrentDate())
                &&filmModel.getMovieEndDate().toDate().after(Helper.getCurrentDate()))
        {
            holder.status.setText("Playing");
        }
        else if (filmModel.getMovieEndDate().toDate().before(Helper.getCurrentDate()) )
        {
            holder.status.setText("Expired");
        }
        else holder.status.setText("Coming");

        if(movieBeginDate.toDate().before(Helper.getCurrentDate())
                &&filmModel.getMovieEndDate().toDate().after(Helper.getCurrentDate()))
        {
            holder.status.setBackgroundColor(Color.TRANSPARENT);
            holder.status.setBackground(ContextCompat.getDrawable(holder.status.getContext(), R.drawable.background_playing));
            if(Users.currentUser.getAccountType().equals("admin")){
                holder.inforBtn.setText("Schedule");
            }
           else holder.inforBtn.setText("Book");
            holder.inforBtn.setBackgroundColor(Color.TRANSPARENT);
            holder.inforBtn.setTextColor(Color.WHITE);
            holder.inforBtn.setBackground(ContextCompat.getDrawable(holder.status.getContext(), R.drawable.background_button));

        }
        else{
            holder.status.setBackgroundColor(Color.TRANSPARENT);
            holder.status.setBackground(ContextCompat.getDrawable(holder.status.getContext(), R.drawable.background_coming));
            holder.inforBtn.setText("Information");
            holder.inforBtn.setBackgroundColor(Color.TRANSPARENT);
            holder.inforBtn.setTextColor(Color.BLACK);
            holder.inforBtn.setBackground(ContextCompat.getDrawable(holder.status.getContext(), R.drawable.background_transparent));

        }
        holder.inforBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                Intent i = new Intent(view.getContext(), InformationFilmActivity.class);
                i.putExtra(ExtraIntent.film, filmModel);
                i.putExtra("type", holder.inforBtn.getText().toString());
                InforBooked.getInstance().film = filmModel;
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }
    void dismissKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


}
