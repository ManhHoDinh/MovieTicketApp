package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
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
import com.example.movieticketapp.Activity.Movie.EditMovieActivity;
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

    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    private AddMovieActivity activity;
    private Uri selectedVideoUri;
    @Override
    public void onBindViewHolder(@NonNull TrailerMovieApdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.playButton.setVisibility(View.INVISIBLE);
            holder.videoSeekBar.setVisibility(View.INVISIBLE);
            holder.endTime.setVisibility(View.INVISIBLE);
            holder.EditTrailer.setVisibility(View.INVISIBLE);
            holder.movietrailer.setBackgroundResource(R.drawable.background_add_movie1);
            holder.texttrailer.setText("Upload Video");
            holder.imtrailer.setImageResource(R.drawable.symbol_image);
            holder.deleteTrailer.setVisibility(View.VISIBLE);
            holder.deleteTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissKeyboard(view);
                    AddMovieActivity.videos.remove(position);
                    AddMovieActivity.videoUris.remove(position);
                    Toast toast = Toast.makeText(holder.itemView.getContext(),"Delete trailer layout success!!!", Toast.LENGTH_SHORT);
                    toast.show();
                    notifyDataSetChanged();
                }
            });

            holder.movietrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissKeyboard(view);
                    activity.pickVideo.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                            .build());
                    setSelectedPosition(position);
                }
            });
            if(!AddMovieActivity.videos.get(position).equals(AddMovieActivity.defaultAddTrailer))
            {
                holder.movietrailer.setBackground(null);
                holder.texttrailer.setText("");
                holder.imtrailer.setImageResource(0);
                holder.playButton.setVisibility(View.VISIBLE);
                holder.videoSeekBar.setVisibility(View.VISIBLE);
                holder.endTime.setVisibility(View.VISIBLE);
                holder.EditTrailer.setVisibility(View.VISIBLE);
                holder.deleteTrailer.setVisibility(View.INVISIBLE);
                holder.movietrailer.setVideoPath(AddMovieActivity.videos.get(position));
                holder.videoSeekBar.setProgress(0);
                holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-0));
                holder.movietrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissKeyboard(view);
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
                holder.movietrailer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        holder.playButton.setImageResource(R.drawable.play_icon);
                        //write your code after complete video play
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
                        dismissKeyboard(view);
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
            if(AddMovieActivity.videoUris.get(position)!=AddMovieActivity.defaultUri)
            {
                if(AddMovieActivity.videoUris.get(position)!=null)
                {
                    holder.movietrailer.setBackground(null);
                    holder.texttrailer.setText("");
                    holder.imtrailer.setImageResource(0);
                    holder.playButton.setVisibility(View.VISIBLE);
                    holder.videoSeekBar.setVisibility(View.VISIBLE);
                    holder.endTime.setVisibility(View.VISIBLE);
                    holder.EditTrailer.setVisibility(View.VISIBLE);
                    holder.deleteTrailer.setVisibility(View.INVISIBLE);
                }
                holder.movietrailer.setVideoURI(AddMovieActivity.videoUris.get(position));
                holder.videoSeekBar.setProgress(0);
                holder.endTime.setText(""+VideoAdapter.convertIntoTime(holder.movietrailer.getDuration()-0));
                holder.movietrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissKeyboard(view);
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
                        if(holder.movietrailer.getDuration()==0)
                        {

                        }
                    }
                });

                holder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissKeyboard(view);
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
                dismissKeyboard(view);
                PopupMenu popup = new PopupMenu(view.getContext(), holder.EditTrailer);
                //inflating menu from xml resource
                popup.inflate(R.menu.promo_menu);
                SpannableString s = new SpannableString("Change");
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
                                TextView textDialog = deleteDialogView.findViewById(R.id.message);
                                textDialog.setText("Do you sure to delete the trailer video?");
                                alertDialog.setView(deleteDialogView);
                                AlertDialog OptionDialog = alertDialog.create();
                                OptionDialog.show();
                                TextView Cancel = deleteDialogView.findViewById(R.id.Cancel_Button);
                                TextView Delete = deleteDialogView.findViewById(R.id.DeleteButton);
                                Delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissKeyboard(view);
                                        AddMovieActivity.videos.remove(position);
                                        AddMovieActivity.videoUris.remove(position);
                                        notifyDataSetChanged();
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

    public TrailerMovieApdapter(AddMovieActivity activity)
    {
        this.activity=activity;
    }
    @Override
    public int getItemCount() {
        return AddMovieActivity.videos.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView movietrailer;
        ImageView imtrailer;
        TextView texttrailer;
        ImageView EditTrailer;
        ImageView playButton;
        ImageView deleteTrailer;

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
            deleteTrailer= itemView.findViewById(R.id.DeleteTrailer);
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
        if (position >= 0 && position < AddMovieActivity.videos.size()) {
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
void dismissKeyboard(View v)
{
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
}



}
