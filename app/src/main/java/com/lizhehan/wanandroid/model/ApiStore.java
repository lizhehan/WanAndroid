package com.lizhehan.wanandroid.model;

import com.lizhehan.wanandroid.model.interceptor.AddCookiesInterceptor;
import com.lizhehan.wanandroid.model.interceptor.ReceivedCookiesInterceptor;
import com.lizhehan.wanandroid.util.ConstantUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api retrofit 工具类
 */

public class ApiStore {

    private volatile static ApiStore uniqueInstance;

    private ApiStore() {
    }

    public static ApiStore getInstance() {
        if (uniqueInstance == null) {
            synchronized (ApiStore.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ApiStore();
                }
            }
        }
        return uniqueInstance;
    }

    public <T> T create(Class<T> service) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(service);
    }

    public <T> T createLogin(Class<T> service) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(service);
    }
}
