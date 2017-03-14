package com.widyan.alamku.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by widyan on 3/14/2017.
 */

public class Utils {
    public static void startThisActivity(Activity ctx, Class classname){
        Intent i = new Intent(ctx, classname);
        ctx.startActivity(i);
    }
}
