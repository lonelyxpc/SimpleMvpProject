package com.lonelyxpc.mvp.net;


import com.lonelyxpc.mvp.api.APIService;
import com.lonelyxpc.mvp.config.Urls;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hzy on 2019/1/10
 * 网络请求
 **/
public class HttpManager {


    private Retrofit mRetrofit;
    private HashMap<Class, Retrofit> mServiceHashMap = new HashMap<>();


    public HttpManager() {
        // init okhttp 3 logger
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        // int okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build();
        // int retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();

        mServiceHashMap.put(APIService.class, mRetrofit);
    }


    public <T> T getService(Class<T> clz) {
        Retrofit retrofit = mServiceHashMap.get(clz);
        if (retrofit != null) {
            T service = retrofit.create(clz);
            return service;
        } else {
            return null;
        }
    }
}