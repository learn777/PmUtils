//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pp.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public final class ScreenUtils {

    public static DisplayMetrics getNetWorkStatus(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;
        return metric;
    }

    public static int dp2px(@NonNull DisplayMetrics metrics, int dp) {
        return (int)(dp * metrics.density - 0.5f);
    }

    public static int px2dp(@NonNull DisplayMetrics metrics, int px) {
        return (int) (px / metrics.density - 0.5f);
    }
}
