package com.example.videoplayer.main;

import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.videoplayer.R;
import com.example.videoplayer.base.BaseActivity;
import com.example.videoplayer.custom_recycler_view.VerticalSpacingItemDecorator;
import com.example.videoplayer.custom_recycler_view.VideoPlayerRecyclerAdapter;
import com.example.videoplayer.custom_recycler_view.VideoPlayerRecyclerView;
import com.example.videoplayer.di.provider.ViewModelFactory;
import com.example.videoplayer.model.Video;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    LinearLayoutManager layoutManager;
    private MainViewModel viewModel;
    private Uri uri;
    private ArrayList<Video> videos;
    private VideoPlayerRecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);

        //generate data
        uri = Uri.parse("http://vidbase.nzdk.ir/videos/1adc/1adc69f2438ca259c2dd99c7527d3309.mp4");
        videos = new ArrayList<Video>();
        videos.add(new Video(uri , "Video 1"));
        videos.add(new Video(uri , "Video 2"));
        videos.add(new Video(uri , "Video 3"));

        recyclerView.setVideos(videos);
        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(videos);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        if (recyclerView != null)
            recyclerView.releasePlayer();
        super.onDestroy();
    }

}

