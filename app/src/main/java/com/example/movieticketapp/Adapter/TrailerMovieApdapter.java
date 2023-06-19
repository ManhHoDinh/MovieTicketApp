package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Movie.AddMovieActivity;
import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;



public class TrailerMovieApdapter extends RecyclerView.Adapter<TrailerMovieApdapter.ViewHolder> {

    private static final int REQUEST_CODE_PICK_VIDEO = 123;
    private int selectedPosition = -1;
    private Uri selectedVideoUri;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    private AddMovieActivity activity;
    @Override
    public void onBindViewHolder(@NonNull TrailerMovieApdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            if(AddMovieActivity.videoUris.get(position)!=AddMovieActivity.defaultUri)
            {
                holder.movietrailer.setBackground(null);
                holder.movietrailer.setVideoURI(AddMovieActivity.videoUris.get(position));
                holder.movietrailer.start();
                if(AddMovieActivity.videoUris.get(position)!=null)
                {
                    holder.texttrailer.setText("");
                    holder.imtrailer.setImageResource(0);
                }

            }        }
        catch (Exception e)
        {}
        holder.movietrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AddMovieActivity.videoUris.get(position)!=AddMovieActivity.defaultUri)
                    holder.movietrailer.start();
                activity.pickVideo.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                            .build());
                setSelectedPosition(position);
            }
        });
      }

    List<String> Videos;
    public TrailerMovieApdapter(List<String> videos, AddMovieActivity activity)
    {
        this.Videos=videos;
        this.activity=activity;
    }
    @Override
    public int getItemCount() {
        return Videos.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView movietrailer;
        ImageView imtrailer;
        TextView texttrailer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movietrailer = (VideoView) itemView.findViewById(R.id.movietrailer);
            imtrailer = (ImageView) itemView.findViewById(R.id.imtrailer);
            texttrailer = (TextView) itemView.findViewById(R.id.texttrailer);
        }
    }
public int getSelectedPosition()
{
    return  selectedPosition;
}
    @NonNull
    @Override
    public TrailerMovieApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_movie, parent, false);
        return new TrailerMovieApdapter.ViewHolder(itemView);
    }

    public void updateVideoElement(Uri videoUri, int position)
    {
        if (position >= 0 && position < Videos.size()) {
            selectedVideoUri = videoUri;
            AddMovieActivity.videoUris.set(position, videoUri);
            notifyItemChanged(position);
        }
    }
//    public interface OnDataChangedListener {
//        void onDataChanged(int totalPrice);
//    }

//    private OnDataChangedListener onDataChangedListener;
//
//    public void setOnDataChangedListener(OnDataChangedListener listener) {
//        this.onDataChangedListener = listener;
//    }

//    public int getTotalPrice() {
//        return TotalPrice;
//    }


}
