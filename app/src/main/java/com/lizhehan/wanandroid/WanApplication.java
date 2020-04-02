package com.lizhehan.wanandroid;

import android.app.Application;

public class WanApplication extends Application {
    private static WanApplication instance;

    public static WanApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
