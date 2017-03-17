package com.widyan.alamku.dao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.widyan.alamku.AlamkuActivity;
import com.widyan.alamku.DetailAlamkuActivity;
import com.widyan.alamku.LoginActivity;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.User;
import com.widyan.alamku.models.UserData;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.SharedPrefData;
import com.widyan.alamku.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by widyan on 3/15/2017.
 */

public class UserDao {
    private APIServices mAPIService;
    private Context ctx;
    public UserDao(Context ctx, APIServices mAPIService) {
        this.mAPIService = mAPIService;
        this.ctx = ctx;
    }

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
                    SharedPrefData.SaveDataUser(ctx.getApplicationContext(), response.body().getData().get(0));
                    Utils.startThisActivity((Activity) ctx, AlamkuActivity.class);
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

    public void register(String first_name, String last_name, String username, String pasword, String bdate, String gender, String phone){
        Log.i("ALAMKU", Constants.Apps.REGISTER);

        mAPIService.register(first_name, last_name, username, pasword, bdate, gender, phone).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.i("ALAMKU","REGISTER DATA BARU = " + response.body().getUsername());
                if(response.isSuccessful()){
                    Log.i("ALAMKU","SUCCESS REGISTER = " + response.body().toString());
                    Utils.startThisActivity((Activity) ctx, LoginActivity.class);
                }else{
                    Log.i("ALAMKU","ERR = " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                t.printStackTrace();
                Log.e("ALAMKU ERR","Unable to submit POST to API");
            }
        });
    }
}
