//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pp.utils.screen;

import android.app.Activity;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public final class ScreenSizeUtils {

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /*
     *Convert DP to PX based on screen information
     */
    public static int dp2px(@NonNull DisplayMetrics metrics, int dp) {
        return (int) (dp * metrics.density - 0.5f);
    }
    /*
     *Convert PX to DP based on screen information
     */
    public static int px2dp(@NonNull DisplayMetrics metrics, int px) {
        return (int) (px / metrics.density - 0.5f);
    }
}
