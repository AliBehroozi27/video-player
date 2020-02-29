package com.example.videoplayer.di.modules;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.videoplayer.custom_recycler_view.VerticalSpacingItemDecorator;
import com.example.videoplayer.custom_recycler_view.VideoPlayerRecyclerAdapter;
import com.example.videoplayer.di.AppComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application);
    }
    
    @Provides
    static VerticalSpacingItemDecorator provideVerticalSpacingItemDecorator(){
        return new VerticalSpacingItemDecorator();
    }
    
    @Provides
    static VideoPlayerRecyclerAdapter provideVideoPlayerRecyclerAdapter(){
        return new VideoPlayerRecyclerAdapter();
    }

}
