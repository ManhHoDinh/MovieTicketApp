package com.example.movieticketapp.Adapter;

import static com.example.movieticketapp.Firebase.FirebaseRequest.mAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.player.setVideoPath(videoUrl);
        holder.player.setOnClickListener(new View.OnClickListener() {
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
                }
                holder.playButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    // Using handler with postDelayed called runnable run method
                    @Override
                    public void run() {
                        holder.playButton.setVisibility(View.INVISIBLE);
                    }
                }, 1*1500); // wait for 5 seconds
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
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (videoIdList != null) return videoIdList.size();
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
        private VideoView player;
        private ImageView playButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            player = itemView.findViewById(R.id.trailer);
            playButton=itemView.findViewById(R.id.PlayButton);
        }
    }
}
