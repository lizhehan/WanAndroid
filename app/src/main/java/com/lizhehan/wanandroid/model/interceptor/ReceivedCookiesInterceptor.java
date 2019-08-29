package com.lizhehan.wanandroid.model.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.lizhehan.wanandroid.application.MyApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        //不为空
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookie = new HashSet<>(originalResponse.headers("Set-Cookie"));
            SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("cookie_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookie);
            editor.apply();
        }
        return originalResponse;
    }
}