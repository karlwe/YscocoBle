package com.ys.module.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
/**
 * 作者：karl.wei
 * 创建日期： 2017/7/31 0031 19:23
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：震动实现
 */
public class TipHelper {
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    /**
     *
     * @param context
     * @param pattern 震动重复的次数
     * @param isRepeat  是否一直重复
     * @param number 不重复状态下播放次数
     *
     */
    public static void Vibrate(final Context context, long[] pattern, boolean isRepeat, int number) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        /**
         * mvibrator.vibrate(new long[]{100,10,100,1000}, -1);//依照指定的模式去震动。
         * 数组參数意义：第一个參数为等待指定时间后開始震动，第二个參数为震动时间。
         *
         * 后边的參数依次为等待震动和震动的时间。
         *
         * 第二个參数为反复次数，-1为不反复。0为一直震动   。
         */
        vib.vibrate(pattern, isRepeat ? 1 : number);
    }

    public static void VibrateCancel(final Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
