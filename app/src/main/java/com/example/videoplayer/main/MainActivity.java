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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int VERTICAL_SPACE_HEIGHT = 10;
    
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    VerticalSpacingItemDecorator itemDecorator;
    @Inject
    VideoPlayerRecyclerAdapter adapter;
    
    @BindView(R.id.recycler_view)
    VideoPlayerRecyclerView recyclerView;
    
    private MainViewModel viewModel;
    private ArrayList<Video> videos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        
        videos =viewModel.getVideos();

        initRecyclerView();
    }

    private void initRecyclerView() {
        
        itemDecorator.setVerticalSpaceHeight(VERTICAL_SPACE_HEIGHT);
        
        adapter.setContext(this);
        adapter.setVideos(videos);
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setVideos(videos);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        if (recyclerView != null)
            recyclerView.releasePlayer();
        super.onDestroy();
    }

}

