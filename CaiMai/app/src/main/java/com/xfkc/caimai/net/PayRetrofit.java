package com.xfkc.caimai.net;


import com.xfkc.caimai.config.Constant;
import com.xfkc.caimai.net.covert.CustomConverterFactory;
import com.xfkc.caimai.net.intercepter.MyRequestIntercepter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by LK on 2017/5/5 10:42.
 */

public class PayRetrofit {
    final PayService service;

    PayRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
//                .addInterceptor(interceptor)
//               .addInterceptor(new RequestItercepter())
               .addInterceptor(new MyRequestIntercepter())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
//                .addConverterFactory(CustomConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(PayService.class);

    }

    public PayService getService() {
        return service;
    }

}
