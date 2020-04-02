package com.lizhehan.wanandroid.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.model.interceptor.AddCookieInterceptor;
import com.lizhehan.wanandroid.model.interceptor.SaveCookieInterceptor;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WanRetrofitService {

    private volatile static WanRetrofitService instance;

    public static WanRetrofitService getInstance() {
        if (instance == null) {
            synchronized (WanRetrofitService.class) {
                if (instance == null) {
                    instance = new WanRetrofitService();
                }
            }
        }
        return instance;
    }

    public WanApi create() {
        return create(Constants.BASE_URL);
    }

    private WanApi create(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                if ((s.startsWith("{") && s.endsWith("}")) || (s.startsWith("[") && s.endsWith("]"))) {
                    JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i("OkHttpLog", "PrettyJson:\n" + gson.toJson(jsonObject));
                } else {
                    Log.i("OkHttpLog", s);
                }
            }
        });
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new SaveCookieInterceptor())
                .addInterceptor(new AddCookieInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WanApi.class);
    }
}
