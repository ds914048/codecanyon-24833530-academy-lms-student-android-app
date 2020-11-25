package com.creativeitem.academy.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;

public class Helpers extends Application {
    public static String SHARED_PREF = "shared_preference";
    public static int convertDpToPixel(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int convertPixelToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
