package com.example.spider.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit mRetrofit = null;
    private static String BASE_URL = "https://impetrosys.com/spiderapp/";




    public static Retrofit getmRetrofitInstance() {

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).build();
        }

        Log.e("ret", BASE_URL);
        return mRetrofit;
    }


}
