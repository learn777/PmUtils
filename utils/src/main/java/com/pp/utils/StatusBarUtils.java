//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pp.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public final class StatusBarUtils {
    /**
     * @author PPM
     * @time 2020/8/22 17:36
     * @param isTranslucent true to translucent status bar, otherwise, status bar not change

     */
    public static void setBarTranslucent(Activity activity, boolean isTranslucent) {
        if (isTranslucent) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.0 全透明实现
                //getWindow.setStatusBarColor(Color.TRANSPARENT)
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //4.4 全透明状态栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
/**
 * @author PPM
 * @time 2020/8/22 17:40
 * @param isDark true to change status bar font color to black, otherwise, no use default
 */
    public static void setStatusBarFontColor(Activity activity, boolean isDark) {
        if (!isDark && VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
