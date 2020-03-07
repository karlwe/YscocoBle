package com.yscoco.blue.utils;

import android.app.Application;

import com.yscoco.blue.exception.BleException;

/**
 * 作者：karl.wei
 * 创建日期： 2020/3/7
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：包名限制
 */
public class PackageNameUtil {
    public static boolean isEffective(Application application){
       String packageName =  application.getPackageName();
        if(packageName.startsWith("com.yscoco.")||packageName.equals("com.yykj.")){
            return true;
        }else{
            throw new BleException("应用包名异常无法正常启动，请联系提供方重新编辑AAR！");
        }
    }
}
