package com.example.videoplayer.model;

public class Video {
    private String videoUrl;
    private String title;
    private String thumbnailUrl;
    
    public Video(String title, String videoUrl, String thumbnailUrl) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
