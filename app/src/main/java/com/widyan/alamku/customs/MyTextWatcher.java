package com.widyan.alamku.customs;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by adhi on 16/10/2016.
 */

public class MyTextWatcher implements TextWatcher {
    private View view;

    public MyTextWatcher(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
