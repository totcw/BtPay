package com.betterda.btpay.api;


import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装网络请求
 * Created by Administrator on 2016/7/29.
 */
public class Api {
    private static ApiService apiService; //封装了请求的接口
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static String URL = "http://101.231.204.84:8091/sim/";

    /**
     * 通过retrofit返回接口的实现类
     * @return
     */
    public static ApiService getNetService() {

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient.newBuilder()
                            .build())
                            .addConverterFactory(gsonConverterFactory)
                            .addCallAdapterFactory(rxJavaCallAdapterFactory)
                            .build();
            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

}
