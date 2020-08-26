package com.ys.module.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/7 0007.
 */
public class AppUtils {
    /**
     * ���
     *
     * @param
     * @param s 有效日期
     * @param dateString 有效期开始时间 格式："2016-1-6" + " " + "12:00:00"
     * @return
     * 获取当前应用是否过期
     */
    public static boolean isExpire(String dateString,int s) {
        Calendar c1 = Calendar.getInstance();
        long criticalTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        Calendar c = Calendar.getInstance();
        try {
            date = sdf.parse(dateString);
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, s);
            criticalTime = c.getTimeInMillis();
            if (criticalTime < c1.getTimeInMillis()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*获取当前版本名称*/
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*获取当前版本*/
    public static int getVersionCode(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
    }
    /*获取设备唯一标识，IMRI*/
    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        if (TextUtils.isEmpty(szImei)){
            return "wew-Android";
        }else{
            return szImei;
        }
    }
}
