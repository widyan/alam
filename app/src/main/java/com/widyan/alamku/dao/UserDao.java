package com.widyan.alamku.dao;

import android.util.Log;

import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.User;
import com.widyan.alamku.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by widyan on 3/15/2017.
 */

public class UserDao {
    private APIServices mAPIService;
    public UserDao(APIServices mAPIService) {
        this.mAPIService = mAPIService;
    }

    public void login(String username, String password){
        Log.i("ALAMKU", Constants.Apps.LOGIN);

        mAPIService.login(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("ALAMKU","DATA BARU = " + response.body().getData().get(0).getUsername());
                if(response.isSuccessful()){
                    Log.i("ALAMKU","SUCCESS = " + response.body().toString());
                }else{
                    Log.i("ALAMKU","ERR = " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Log.e("ALAMKU ERR","Unable to submit POST to API");
            }
        });
    }
}
