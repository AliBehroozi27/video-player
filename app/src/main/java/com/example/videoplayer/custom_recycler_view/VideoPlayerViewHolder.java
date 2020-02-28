package com.example.videoplayer.custom_recycler_view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;
import com.google.android.exoplayer2.ui.PlayerView;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    PlayerView surfaceView;
    FrameLayout media_container;
    TextView title;
    ImageView volumeControl;
    ProgressBar progressBar;
    View parent;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.media_container);
        title = itemView.findViewById(R.id.title);
        progressBar = itemView.findViewById(R.id.progressBar);
        surfaceView = itemView.findViewById(R.id.player_view);
        volumeControl = itemView.findViewById(R.id.volume_control);
    }

    public void onBind(Video video) {
        parent.setTag(this);
        title.setText(video.getTitle());
    }

}














