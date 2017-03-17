package com.widyan.alamku.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.widyan.alamku.models.UserData;

/**
 * Created by widyan on 3/17/2017.
 */

public class SharedPrefData {
    public static void SaveDataUser(Context ctx, UserData userData){
        final Gson gson = new Gson();
        CacheManager.writeAllCachedText(ctx, Constants.Preferences.USER_DATA, gson.toJson(userData));
    }

    public static void Logout(Context ctx){
        CacheManager.writeAllCachedText(ctx,Constants.Preferences.USER_DATA, "");
    }

    public static UserData GetSaveDataUser(Context ctx) throws Exception{
        final Gson gson = new Gson();
        String jsonText = CacheManager.readAllCachedText(ctx, Constants.Preferences.USER_DATA);
        if(jsonText == null || jsonText.equals("")){
            //throw new Exception();
        }
        return gson.fromJson(jsonText, UserData.class);
    }
}
