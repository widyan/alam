package com.widyan.alamku;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.widyan.alamku.utils.Utils;

/**
 * Created by widyan on 3/14/2017.
 */

public class IntroActivity extends AppIntro {

    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();
        addSlide(AppIntro2Fragment.newInstance(res.getString(R.string.app_name),res.getString(R.string.desc_intro_1),R.drawable.hiking_logo_white,res.getColor(R.color.colorPrimary)));
        addSlide(AppIntro2Fragment.newInstance(res.getString(R.string.title_intro_1),res.getString(R.string.desc_intro_2),R.drawable.intro1,res.getColor(R.color.colorPrimary)));
        addSlide(AppIntro2Fragment.newInstance(res.getString(R.string.title_intro_2),res.getString(R.string.desc_intro_3),R.drawable.intro2,res.getColor(R.color.colorPrimary)));

        showSkipButton(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        Utils.startThisActivity(IntroActivity.this, LoginActivity.class);
    }
}
