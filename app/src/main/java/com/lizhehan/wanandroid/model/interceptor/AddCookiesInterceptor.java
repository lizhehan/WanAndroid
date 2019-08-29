package com.lizhehan.wanandroid.model.interceptor;

import android.content.Context;

import com.lizhehan.wanandroid.application.MyApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> hashSet = (HashSet<String>) MyApplication.getInstance().getSharedPreferences("cookie_data", Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (hashSet != null) {
            for (String cookie : hashSet) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}