package com.widyan.alamku.customs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by adhi on 23/12/2015.
 */
public class CustomTextViewBoldRoboto extends AppCompatTextView {

    public CustomTextViewBoldRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewBoldRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBoldRoboto(Context context) {
        super(context);
        init();
    }

    private void init() {
        //Typeface typefaceBold = Constants.FontTypeMango.boldRobotoMangoTypeface(this);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "RobotoCondensed-Bold.ttf");
        setTypeface(tf);
    }

}
