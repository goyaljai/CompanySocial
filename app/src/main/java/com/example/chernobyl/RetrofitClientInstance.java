package com.example.chernobyl;

import com.squareup.moshi.Moshi;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.Retrofit;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://13.233.83.239/";

    public static Retrofit getRetrofitInstance() {
        Moshi moshi = new Moshi.Builder().build();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();
        }
        return retrofit;
    }
}
