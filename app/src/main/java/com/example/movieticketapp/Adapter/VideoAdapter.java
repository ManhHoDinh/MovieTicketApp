package com.example.movieticketapp.Adapter;

import static com.example.movieticketapp.Firebase.FirebaseRequest.mAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Account.OnboardingActivity;
import com.example.movieticketapp.Activity.Account.SignInActivity;
import com.example.movieticketapp.Activity.Account.SplashActivity;
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Activity.Movie.AddMovieActivity;
import com.example.movieticketapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    List<String> videoIdList;

    public void setVideoIdList(List<String> list)
    {
        this.videoIdList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoUrl = videoIdList.get(position);
        if (videoUrl == null) return;
        holder.bindVideo(videoUrl);
        holder.videoSeekBar.setProgress(0);
        holder.endTime.setText(""+convertIntoTime(holder.player.getDuration()-0));

        holder.player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.playButton.setVisibility(View.VISIBLE);
                holder.videoSeekBar.setVisibility(View.VISIBLE);
                holder.endTime.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {

                    // Using handler with postDelayed called runnable run method
                    @Override
                    public void run() {
                        if(holder.player.isPlaying())
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
                        holder.player.seekTo(progress);
                        int currentPosition = holder.player.getCurrentPosition();
                        holder.endTime.setText("" + convertIntoTime(holder.player.getDuration() - currentPosition));
                    }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.player.isPlaying())
                {
                    holder.player.pause();
                    holder.playButton.setImageResource(R.drawable.play_icon);
                 }
                else
                {
                    holder.playButton.setImageResource(R.drawable.pause_icon);
                    holder.player.start();
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
                if (holder.player.getDuration()>0){
                    int currentPosition = holder.player.getCurrentPosition();
                    holder.videoSeekBar.setProgress(currentPosition);
                    holder.endTime.setText(""+convertIntoTime(holder.player.getDuration()-currentPosition));
                }
                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,500);
        holder.player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                holder.playButton.setImageResource(R.drawable.play_icon);
                //write your code after complete video play
            }
        });

    }

    @Override
    public int getItemCount() {
        if (videoIdList != null) return videoIdList.size();
        return 0;
    }
    public static String convertIntoTime(int ms){
        String time;
        int x, seconds, minutes, hours;
        x = ms / 1000;
        seconds = x % 60;
        x /= 60;
        minutes = x % 60;
        x /= 60;
        hours = x % 24;
        if (hours != 0)
            time = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        else time = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        return time;
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
        private VideoView player;
        private ImageView playButton;
        SeekBar videoSeekBar;
        TextView endTime;
        private ProgressBar progressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            player = itemView.findViewById(R.id.trailer);
            playButton=itemView.findViewById(R.id.PlayButton);
            videoSeekBar = itemView.findViewById(R.id.videoView_seekbar);
            endTime = itemView.findViewById(R.id.videoView_endtime);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
        public void bindVideo(String videoUrl) {
            showLoading();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    hideLoading();
                    // Start video playback here if desired
                   // videoView.start();
                    videoSeekBar.setMax(player.getDuration());
                }
            });

            player.setVideoPath(videoUrl);
            player.requestFocus();
        }

        private void showLoading() {
            progressBar.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
            videoSeekBar.setVisibility(View.GONE);
            endTime.setVisibility(View.GONE);
        }

        private void hideLoading() {
            progressBar.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
            videoSeekBar.setVisibility(View.VISIBLE);
            endTime.setVisibility(View.VISIBLE);
        }
    }
}
