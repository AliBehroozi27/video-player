package com.example.videoplayer;

import android.content.Context;
import android.util.TypedValue;

import com.example.videoplayer.main.Content;

import javax.inject.Inject;

public class Util {
    private Context context;

    public Util(Context context) {
        this.context = context;
    }

    public int getDp() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
    }
}
