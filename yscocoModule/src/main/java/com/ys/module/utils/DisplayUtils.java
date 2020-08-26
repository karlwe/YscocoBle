package com.ys.module.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.ys.module.log.LogUtils;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
public class DisplayUtils {

    public static void print(Activity context) {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        LogUtils.e("w:" + screenWidth + ", h:" + screenHeight);


        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels*dm.density;
        float height=dm.heightPixels*dm.density;

        LogUtils.e("w:" + width + ", h:" + height + ",d:" + dm.densityDpi + "::" + dm.density);
    }

    public static float dp2px(float dp,Activity activity) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                activity.getResources().getDisplayMetrics());
    }
    /*单位5mm的宽度*/
    public static float getPxUnitCm(Activity context) {
        Point point = new Point();
        context.getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float pxunit =(float) ( dm.xdpi/5.08);
        return pxunit;
    }
}
