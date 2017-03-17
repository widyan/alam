package com.widyan.alamku.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;

import com.widyan.alamku.api.RetrofitClient;
import com.widyan.alamku.api.ServiceGenerator;
import com.widyan.alamku.interfaces.api.APIServices;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by widyan on 3/14/2017.
 */

public class Utils {
    public static void startThisActivity(Activity ctx, Class classname){
        Intent i = new Intent(ctx, classname);
        ctx.startActivity(i);
        ctx.finish();
    }

    public static void startThisActivityWithParams(Activity ctx, Class classname, ArrayList data, String key){
        Intent i = new Intent(ctx, classname);
        i.putStringArrayListExtra(key, data);
        ctx.startActivity(i);
        ctx.finish();
    }

    public static void startThisActivityWithParamString(Activity ctx, Class classname, String data, String key){
        Intent i = new Intent(ctx, classname);
        i.putExtra(key, data);
        ctx.startActivity(i);
        ctx.finish();
    }

    public static boolean validationInput(EditText editText, String errormsg){
        if(TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(errormsg);
            editText.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static APIServices getAPIServiceGenerator(){
        return ServiceGenerator.createService(APIServices.class);
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
