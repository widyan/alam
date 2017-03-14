package com.widyan.alamku.customs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by adhi on 26/05/2016.
 */
public class CustomEditTextLatoLight extends AppCompatEditText {

    public CustomEditTextLatoLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomEditTextLatoLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditTextLatoLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        //Typeface typefaceBold = Constants.FontTypeMango.boldRobotoMangoTypeface(this);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Lato-Light.ttf");
        setTypeface(tf);
    }
}
