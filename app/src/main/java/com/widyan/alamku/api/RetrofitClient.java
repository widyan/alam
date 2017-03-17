package com.widyan.alamku.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by widyan on 3/15/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder = null;
    public static Retrofit getClient(String baseURL){
        if(retrofit == null){
            retrofit = getBuilder(baseURL).build();
            /*retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/
        }
        return retrofit;
    }

    public static Retrofit.Builder getBuilder(String baseURL){
        if(builder == null){
            builder =
                    new Retrofit.Builder()
                            .baseUrl(baseURL)
                            .addConverterFactory(GsonConverterFactory.create());
        }
        return builder;

    }
}
