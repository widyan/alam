package com.widyan.alamku;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.widyan.alamku.utils.SharedPrefData;
import com.widyan.alamku.utils.Utils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                boolean isFirstStart = getPref.getBoolean("firststart", true);

                try {
                    if (isFirstStart) {
                        Utils.startThisActivity(MainActivity.this, IntroActivity.class);
                        finish();
                        SharedPreferences.Editor e = getPref.edit();
                        e.putBoolean("firststart", false);
                        e.apply();
                    } else {
                        if (SharedPrefData.GetSaveDataUser(MainActivity.this) == null) {
                            Utils.startThisActivity(MainActivity.this, LoginActivity.class);
                            finish();
                        } else {
                            Utils.startThisActivity(MainActivity.this, AlamkuActivity.class);
                            finish();
                        }
                    }
                } catch (NullPointerException e) {
                    Log.e("ALAMKU","ERR Null MainActivity = " + e.getMessage());
                } catch (Exception e) {
                    Log.e("ALAMKU","ERR MainActivity = " + e.getMessage());
                }
            }
        });
        t.start();
    }
}
