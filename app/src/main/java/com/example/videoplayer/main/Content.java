package com.example.videoplayer.main;

import com.example.videoplayer.model.Video;

import java.util.ArrayList;


public class Content {
    
    private static final String V1 = "file:///android_asset/videos/vid1.mp4";
    private static final String I1 = "thumbnails/im1.jpeg";
    
    private static final String V2 = "file:///android_asset/videos/vid2.mp4";
    private static final String I2 = "thumbnails/im2.jpeg";
    
    private static final String V3 = "file:///android_asset/videos/vid3.mp4";
    private static final String I3 = "thumbnails/im3.jpeg";
    
    private static final String V4 = "file:///android_asset/videos/vid4.mp4";
    private static final String I4 = "thumbnails/im4.jpeg";
    
    private static final String V5 = "file:///android_asset/videos/vid5.mp4";
    private static final String I5 = "thumbnails/im5.jpeg";
    
    
    private ArrayList<Video> items = new ArrayList<Video>();
    
    public Content() {
        initItems();
    }
    
    public ArrayList<Video> getItems() {
        return items;
    }
    
    private void initItems() {
        items.add(new Video("video 1", V1, I1));
        items.add(new Video("video 2", V2, I2));
        items.add(new Video("video 3", V3, I3));
        items.add(new Video("video 4", V4, I4));
        items.add(new Video("video 5", V5, I5));
        }
}