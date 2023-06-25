package com.example.movieticketapp.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.SeekBar;
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

import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.Movie.EditMovieActivity;
import com.example.movieticketapp.Activity.Movie.InformationFilmActivity;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
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



public class EditTrailerAdapter extends RecyclerView.Adapter<EditTrailerAdapter.ViewHolder> {

    private static final int REQUEST_CODE_PICK_VIDEO = 123;
    private int selectedPosition = -1;
    private Uri selectedVideoUri;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    private EditMovieActivity activity;
    @Override
    public void onBindViewHolder(@NonNull EditTrailerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.playButton.setVisibility(View.INVISIBLE);
            holder.videoSeekBar.setVisibility(View.INVISIBLE);
            holder.endTime.setVisibility(View.INVISIBLE);
            holder.EditTrailer.setVisibility(View.INVISIBLE);
            if(!Videos.get(position).equals(EditMovieActivity.defaultAddTrailer))
            {
                holder.movietrailer.setBackground(null);
                holder.texttrailer.setText("");
                holder.imtrailer.setImageResource(0);
                holder.playButton.setVisibility(View.VISIBLE);
                holder.videoSeekBar.setVisibility(View.VISIBLE);
                holder.endTime.setVisibility(View.VISIBLE);
                holder.EditTrailer.setVisibility(View.VISIBLE);
                holder.movietrailer.setVideoPath(Videos.get(position));
                holder.videoSeekBar.setProgress(0);
                holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-0));
                holder.movietrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.playButton.setVisibility(View.VISIBLE);
                        holder.videoSeekBar.setVisibility(View.VISIBLE);
                        holder.endTime.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {

                            // Using handler with postDelayed called runnable run method
                            @Override
                            public void run() {
                                if(holder.movietrailer.isPlaying())
                                {
                                    holder.playButton.setVisibility(View.INVISIBLE);
                                    holder.videoSeekBar.setVisibility(View.INVISIBLE);
                                    holder.endTime.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, 1*5000); // wait for 5 seconds

                    }

                });
                holder.videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            holder.movietrailer.seekTo(progress);
                            int currentPosition = holder.movietrailer.getCurrentPosition();
                            holder.endTime.setText("" + VideoAdapter.convertIntoTime(holder.movietrailer.getDuration() - currentPosition));
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                holder.movietrailer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.videoSeekBar.setMax(holder.movietrailer.getDuration());
                    }
                });

                holder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(holder.movietrailer.isPlaying())
                        {
                            holder.movietrailer.pause();
                            holder.playButton.setImageResource(R.drawable.play_icon);
                        }
                        else
                        {
                            holder.playButton.setImageResource(R.drawable.pause_icon);
                            holder.movietrailer.start();
                            new Handler().postDelayed(new Runnable() {

                                // Using handler with postDelayed called runnable run method
                                @Override
                                public void run() {
                                    holder.playButton.setVisibility(View.INVISIBLE);
                                    holder.videoSeekBar.setVisibility(View.INVISIBLE);
                                    holder.endTime.setVisibility(View.INVISIBLE);
                                }
                            }, 1*5000); // wait for 5 seconds
                        }
                    }
                });
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (holder.movietrailer.getDuration()>0){
                            int currentPosition = holder.movietrailer.getCurrentPosition();
                            holder.videoSeekBar.setProgress(currentPosition);
                            holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-currentPosition));
                        }
                        handler.postDelayed(this,0);
                    }
                };
                handler.postDelayed(runnable,500);
            }
            if(EditMovieActivity.videoUris.get(position)!=EditMovieActivity.defaultUri)
            {
                if(EditMovieActivity.videoUris.get(position)!=null)
                {
                    holder.movietrailer.setBackground(null);
                    holder.texttrailer.setText("");
                    holder.imtrailer.setImageResource(0);
                    holder.playButton.setVisibility(View.VISIBLE);
                    holder.videoSeekBar.setVisibility(View.VISIBLE);
                    holder.endTime.setVisibility(View.VISIBLE);
                    holder.EditTrailer.setVisibility(View.VISIBLE);
                }
                holder.movietrailer.setVideoURI(EditMovieActivity.videoUris.get(position));
                holder.videoSeekBar.setProgress(0);
                holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-0));
                holder.movietrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.playButton.setVisibility(View.VISIBLE);
                        holder.videoSeekBar.setVisibility(View.VISIBLE);
                        holder.endTime.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {

                            // Using handler with postDelayed called runnable run method
                            @Override
                            public void run() {
                                if(holder.movietrailer.isPlaying())
                                {
                                    holder.playButton.setVisibility(View.INVISIBLE);
                                    holder.videoSeekBar.setVisibility(View.INVISIBLE);
                                    holder.endTime.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, 1*5000); // wait for 5 seconds

                    }

                });
                holder.videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            holder.movietrailer.seekTo(progress);
                            int currentPosition = holder.movietrailer.getCurrentPosition();
                            holder.endTime.setText("" + VideoAdapter.convertIntoTime(holder.movietrailer.getDuration() - currentPosition));
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                holder.movietrailer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.videoSeekBar.setMax(holder.movietrailer.getDuration());

                    }
                });

                holder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(holder.movietrailer.isPlaying())
                        {
                            holder.movietrailer.pause();
                            holder.playButton.setImageResource(R.drawable.play_icon);
                        }
                        else
                        {
                            holder.playButton.setImageResource(R.drawable.pause_icon);
                            holder.movietrailer.start();
                            new Handler().postDelayed(new Runnable() {

                                // Using handler with postDelayed called runnable run method
                                @Override
                                public void run() {
                                    holder.playButton.setVisibility(View.INVISIBLE);
                                    holder.videoSeekBar.setVisibility(View.INVISIBLE);
                                    holder.endTime.setVisibility(View.INVISIBLE);
                                }
                            }, 1*5000); // wait for 5 seconds
                        }
                    }
                });
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (holder.movietrailer.getDuration()>0){
                            int currentPosition = holder.movietrailer.getCurrentPosition();
                            holder.videoSeekBar.setProgress(currentPosition);
                            holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-currentPosition));
                        }
                        handler.postDelayed(this,0);
                    }
                };
                handler.postDelayed(runnable,500);

            }
        }
        catch (Exception e)
        {}
        holder.EditTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.EditTrailer);
                //inflating menu from xml resource
                popup.inflate(R.menu.promo_menu);
                SpannableString s = new SpannableString("Edit");
                s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
                popup.getMenu().getItem(0).setTitle(s);
                SpannableString delete = new SpannableString("Delete");
                delete.setSpan(new ForegroundColorSpan(Color.RED), 0, delete.length(), 0);
                popup.getMenu().getItem(1).setTitle(delete);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.promo_edit:
                                //handle menu1 click
                            {
                                activity.pickVideo.launch(new PickVisualMediaRequest.Builder()
                                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                                        .build());
                                setSelectedPosition(position);
                            }
                            return true;
                            case R.id.promo_delete: {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext(), R.style.CustomAlertDialog);
                                LayoutInflater factory = LayoutInflater.from(holder.itemView.getContext());
                                final View deleteDialogView = factory.inflate(R.layout.yes_no_dialog, null);
                                alertDialog.setView(deleteDialogView);
                                AlertDialog OptionDialog = alertDialog.create();
                                OptionDialog.show();
                                TextView Cancel = deleteDialogView.findViewById(R.id.Cancel_Button);
                                TextView Delete = deleteDialogView.findViewById(R.id.DeleteButton);
                                Delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        OptionDialog.dismiss();
                                    }
                                });
                                Cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        OptionDialog.dismiss();
                                    }
                                });
                            }
                            return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

    }

    List<String> Videos;
    public EditTrailerAdapter(List<String> videos, EditMovieActivity activity)
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
        ImageView EditTrailer;
        ImageView playButton;
        SeekBar videoSeekBar;
        TextView endTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movietrailer = (VideoView) itemView.findViewById(R.id.movietrailer);
            imtrailer = (ImageView) itemView.findViewById(R.id.imtrailer);
            texttrailer = (TextView) itemView.findViewById(R.id.texttrailer);
            EditTrailer= itemView.findViewById(R.id.EditTrailer);
            playButton=itemView.findViewById(R.id.PlayButton);
            videoSeekBar = itemView.findViewById(R.id.videoView_seekbar);
            endTime = itemView.findViewById(R.id.videoView_endtime);

        }
    }
    public int getSelectedPosition()
    {
        return  selectedPosition;
    }
    @NonNull
    @Override
    public EditTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_trailer_movie, parent, false);
        return new EditTrailerAdapter.ViewHolder(itemView);
    }

    public void updateVideoElement(Uri videoUri, int position)
    {
        if (position >= 0 && position < Videos.size()) {
            selectedVideoUri = videoUri;
            EditMovieActivity.videoUris.set(position, videoUri);
            notifyItemChanged(position);
        }
    }
}

