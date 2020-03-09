package com.example.videoplayer.main;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoplayer.Util;
import com.example.videoplayer.model.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private static final String TAG = "ViewModel";
    @Inject
    Util util;

    private Content content;

    @Inject
    public MainViewModel() {
        content = new Content();
        Log.e(TAG, "MainViewModel: " + util);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public ArrayList<Video> getVideos() {
        return content.getItems();
    }
}
