package com.example.videoplayer.di.modules;

import com.example.videoplayer.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract MainActivity contrbuteMainActivity();
}
