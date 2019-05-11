package com.buddman.zepespot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wonny on 2017. 1. 6..
 */

public class NetworkServiceGenerator {

    private static NetworkServiceGenerator sInstance = null;

    private Gson gson = new GsonBuilder()
//            .setDateFormat(Constant.DATETIME_FORMAT)
            .setLenient()
            .create();

    private Retrofit.Builder serverBuilder = new Retrofit.Builder()
            .baseUrl("")    // 테스트
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    private Retrofit.Builder zepetoBuilder = new Retrofit.Builder()
            .baseUrl("http://47.74.149.35/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    private OkHttpClient.Builder httpClientBuilder;

    private NetworkServiceGenerator() {
        this.httpClientBuilder = createHttpClientBuilder();
    }

    public static NetworkServiceGenerator getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkServiceGenerator();
        }
        return sInstance;
    }

    private OkHttpClient.Builder createHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor.Logger logger = message -> {};

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger);

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging).connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS);
        return builder;
    }

    public <S> S createService(Class<S> serviceClass) {
        serverBuilder.client(httpClientBuilder.build());
        Retrofit retrofit = serverBuilder.build();
        return retrofit.create(serviceClass);
    }
    public <S> S createSpringService(Class<S> serviceClass) {
        zepetoBuilder.client(httpClientBuilder.build());
        Retrofit retrofit = zepetoBuilder.build();
        return retrofit.create(serviceClass);
    }
}
