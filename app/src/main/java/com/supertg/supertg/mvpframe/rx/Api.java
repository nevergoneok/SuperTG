package com.supertg.supertg.mvpframe.rx;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supertg.supertg.base.BaseApplication;
import com.supertg.supertg.constant.UrlConstants;
import com.supertg.supertg.mvpframe.rx.apiservice.ApiService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by helin on 2016/11/10 10:28.
 */

public class Api {
    private static ApiService SERVICE;
    private final Gson mGsonDateFormat;
    /**
     * 请求超时时间
     */
    private final static long DEFAULT_TIMEOUT = 10;

    public Api() {
        mGsonDateFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return Api.SingletonHolder.INSTANCE;
    }

    public ApiService getDefault() {
        if (SERVICE == null) {
            SERVICE = new Retrofit.Builder()
//                    .client(httpClientBuilder.build())
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(UrlConstants.拿软妹子)
                    .build().create(ApiService.class);
        }
        return SERVICE;
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttpClient
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        /**
//         *  拦截器
//         */
//        httpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
////                    Request.Builder requestBuilder = request.newBuilder();
////                    RequestBody formBody = new FormBody.Builder()
////                            .add("1","2")
////                            .build();
//
//                HttpUrl.Builder authorizedUrlBuilder = request.url()
//                        .newBuilder()
//                        //添加统一参数 如手机唯一标识符,token等
//                        .addQueryParameter("key1", "value1")
//                        .addQueryParameter("key2", "value2");
//
//                Request newRequest = request.newBuilder()
//                        //对所有请求添加请求头
//                        .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
//                        .method(request.method(), request.body())
//                        .url(authorizedUrlBuilder.build())
//                        .build();
//
////                    okhttp3.Response originalResponse = chain.proceed(request);
////                    return originalResponse.newBuilder().header("mobileFlag", "adfsaeefe").addHeader("type", "4").build();
//                return chain.proceed(newRequest);
//            }
//        });
        //设置缓存  okhttp缓存
        File httpCacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }

}
