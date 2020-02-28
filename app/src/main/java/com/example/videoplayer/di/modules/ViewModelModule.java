package com.example.videoplayer.di.modules;



import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.videoplayer.di.provider.ViewModelFactory;
import com.example.videoplayer.di.utils.ViewModelKey;
import com.example.videoplayer.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindListViewModel(MainViewModel mainViewModel);
    

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
