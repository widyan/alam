package com.widyan.alamku.api;

import com.widyan.alamku.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by widyan on 3/16/2017.
 */

public class ServiceGenerator {

    private static Retrofit.Builder builder = RetrofitClient.getBuilder(Constants.Apps.BASE_URL);
    private static Retrofit retrofit = RetrofitClient.getClient(Constants.Apps.BASE_URL);

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

}
