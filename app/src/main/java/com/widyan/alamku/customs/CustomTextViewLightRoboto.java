package com.widyan.alamku.customs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by adhi on 23/12/2015.
 */
public class CustomTextViewLightRoboto extends AppCompatTextView {

    public CustomTextViewLightRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewLightRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewLightRoboto(Context context) {
        super(context);
        init();
    }

    private void init() {
        //Typeface typefaceBold = Constants.FontTypeMango.boldRobotoMangoTypeface(this);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Light.ttf");
        setTypeface(tf);
    }
}
