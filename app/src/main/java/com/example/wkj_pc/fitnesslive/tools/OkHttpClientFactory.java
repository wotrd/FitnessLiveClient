package com.example.wkj_pc.fitnesslive.tools;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wkj_pc on 2017/8/6.
 */

public class OkHttpClientFactory {
    private static OkHttpClient client;
    private OkHttpClientFactory(){
         client= new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                 .writeTimeout(10,TimeUnit.SECONDS)
                 .readTimeout(10,TimeUnit.SECONDS).
                 build();
    }
    public static OkHttpClient getOkHttpClientInstance(){
        if (null==client)
                new OkHttpClientFactory();
        return client;
    }
}
