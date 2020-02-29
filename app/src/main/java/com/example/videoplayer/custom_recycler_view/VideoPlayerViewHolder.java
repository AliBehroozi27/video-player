package com.example.videoplayer.custom_recycler_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {
    
    @BindView(R.id.media_container)
    FrameLayout media_container;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.volume_control)
    ImageView volumeControl;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    
    View parent;
    
    private static final String TAG = "ViewHolder";

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        
        ButterKnife.bind(this , itemView);
        
        parent = itemView;
    }
    
    public void onBind(Video video, Context context) {
        parent.setTag(this);
        title.setText(video.getTitle());
        
        // convert asset file to drawable
        InputStream ims = null;
        try {
            ims = context.getAssets().open(video.getThumbnailUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(ims, null);
        
        thumbnail.setImageDrawable(d);
    }

}














