package com.lizhehan.wanandroid.model.interceptor;

import android.content.Context;

import com.lizhehan.wanandroid.WanApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookieInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String requestUrl = chain.request().url().toString();
        HashSet<String> hashSet = (HashSet<String>) WanApplication.getInstance().getSharedPreferences("cookie", Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (!(requestUrl.contains("user/login") || requestUrl.contains("user/register")) && hashSet != null) {
            for (String cookie : hashSet) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}