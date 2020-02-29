package com.example.videoplayer.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoplayer.model.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    
    
    private Content content;
    
    @Inject
    public MainViewModel() {
        content = new Content();
    }
    
    
    @Override
    protected void onCleared() {
        super.onCleared();
    }
    
    public ArrayList<Video> getVideos() {
        return content.getItems();
    }
}
