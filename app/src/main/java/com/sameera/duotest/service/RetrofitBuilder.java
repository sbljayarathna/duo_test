package com.sameera.duotest.service;

import com.sameera.duotest.config.AppConst;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sameera on 6/16/17.
 */

public class RetrofitBuilder {
    private static Retrofit RETROFIT;
    private static Retrofit RETROFIT1;

    public static Retrofit getRetrofitInstanceUS() {
        if (RETROFIT == null) {
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(AppConst.BASE_URL_USER_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return RETROFIT;
    }

    public static Retrofit getRetrofitInstanceLT() {
        if (RETROFIT1 == null) {
            RETROFIT1 = new Retrofit.Builder()
                    .baseUrl(AppConst.BASE_URL_LITE_TICKET)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return RETROFIT1;
    }
}