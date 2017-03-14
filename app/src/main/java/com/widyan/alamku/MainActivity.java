package com.widyan.alamku;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.widyan.alamku.utils.Utils;

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

                if(isFirstStart){
                    Utils.startThisActivity(MainActivity.this, IntroActivity.class);
                    SharedPreferences.Editor e = getPref.edit();
                    e.putBoolean("firststart", false);
                    e.apply();
                }else{
                    finish();
                    Utils.startThisActivity(MainActivity.this, LoginActivity.class);
                }
            }
        });
        t.start();
    }
}
