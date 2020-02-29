package com.example.videoplayer.custom_recycler_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

import java.util.ArrayList;


public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "VideoPlayerRecycler";
    private ArrayList<Video> videos;
    private Context context;
    
    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
    
    public void setContext(Context context) {
        this.context = context;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_video_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.e(TAG, "onBindViewHolder: " + i);
        ((VideoPlayerViewHolder) viewHolder).onBind(videos.get(i) , context);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

}














