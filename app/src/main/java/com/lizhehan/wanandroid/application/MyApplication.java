package com.lizhehan.wanandroid.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.MutableContextWrapper;
import android.webkit.WebView;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        WebView mWebView = new WebView(new MutableContextWrapper(this));
    }
}
