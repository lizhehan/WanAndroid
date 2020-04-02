package com.lizhehan.wanandroid.model.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.lizhehan.wanandroid.WanApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookieInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String requestUrl = request.url().toString();
        if ((requestUrl.contains("user/login") || requestUrl.contains("user/register")) && !response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookie = new HashSet<>(response.headers("Set-Cookie"));
            SharedPreferences sharedPreferences = WanApplication.getInstance().getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookie);
            editor.apply();
        }
        return response;
    }
}