package com.widyan.alamku.utils;

import android.app.Activity;
import android.content.Intent;

import com.widyan.alamku.api.RetrofitClient;
import com.widyan.alamku.interfaces.api.APIServices;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by widyan on 3/14/2017.
 */

public class Utils {
    public static void startThisActivity(Activity ctx, Class classname){
        Intent i = new Intent(ctx, classname);
        ctx.startActivity(i);
    }

    public static APIServices getAPIService() {

        return RetrofitClient.getClient(Constants.Apps.BASE_URL).create(APIServices.class);
    }

    public static String md5(String text){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(text.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for(int i = 0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length()<2)
                    h = "0"+h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
