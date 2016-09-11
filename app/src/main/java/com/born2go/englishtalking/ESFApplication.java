package com.born2go.englishtalking;

import android.app.Application;

/**
 * Created by Hue on 8/16/2016.
 */
public class ESFApplication extends Application {

    public ESFApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initESFSingleton();

    }
    protected void initESFSingleton() {
        // Initialize the instance of TextToSpeechSingleton
        ESFSingleton.initInstance(getApplicationContext());
    }
}
