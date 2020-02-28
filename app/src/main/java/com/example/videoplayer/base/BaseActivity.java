package com.example.videoplayer.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;


import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.annotations.Nullable;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}