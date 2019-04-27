package com.yscoco.blue.app;

import android.app.Application;

import com.yscoco.blue.BleManage;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/9 0009 13:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：配置蓝牙主应用
 */
public class BaseBlueApplication extends Application {

    public static BaseBlueApplication getApplication() {
        return instance;
    }
    private static BaseBlueApplication instance;
    public BaseBlueApplication() {
        instance = this;
    }

    public static BaseBlueApplication getInstance() {
        if(instance==null){
            synchronized (BaseBlueApplication.class) {
                if(instance==null) {
                    instance = new BaseBlueApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BleManage.getInstance().init(this,null);
    }
}
