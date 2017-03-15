package com.widyan.alamku.interfaces.api;

import android.util.Log;

import com.widyan.alamku.models.User;
import com.widyan.alamku.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by widyan on 3/15/2017.
 */

public interface APIServices {
    @POST(Constants.Apps.LOGIN)
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);
}
