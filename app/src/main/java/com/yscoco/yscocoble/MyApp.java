package com.yscoco.yscocoble;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.ys.module.log.LogUtils;
import com.yscoco.blue.BleConfig;
import com.yscoco.blue.BleManage;
import com.yscoco.blue.app.BaseBlueApplication;
import com.yscoco.blue.bean.NotifyUUIDBean;
import com.yscoco.blue.imp.SingleBleDriver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 作者：karl.wei
 * 创建日期： 2017/8/9 0009 14:11
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：主Application
 */
public class MyApp extends BaseBlueApplication {

    private static MyApp instance;
//    private RefWatcher mRefWatcher;

    private DisplayMetrics displayMetrics = null;

    public MyApp() {
        instance = this;
    }

    public static MyApp getApplication() {
        if (instance == null) {
            instance = new MyApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        /*关闭日志*/
        closeLog(true);
        initBle();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// save in SD card first
            Constans.RootPath = Environment.getExternalStorageDirectory() + "/Bracelet";
        } else {
            Constans.RootPath = this.getFilesDir().getAbsolutePath() + File.separator + "Bracelet";
        }
    }

    private void initBle() {

        BleConfig config = new BleConfig();
        config.SERVICE_UUID1 = BleConstans.SERVICE_UUID1;
        config.CHA_NOTIFY =    BleConstans.CHA_NOTIFY;
        config.CHA_WRITE =     BleConstans.CHA_WRITE;
        List<NotifyUUIDBean> beansList = new ArrayList<NotifyUUIDBean>();
        beansList.add(new NotifyUUIDBean(BleConstans.SERVICE_BATTERY_UUID,BleConstans.CHA_BATTERY_NOTIFY));
        beansList.add(new NotifyUUIDBean(BleConstans.SERVICE_UUID1,BleConstans.CHA_NOTIFY));
        config.setNotifyList(beansList);
        config.setFileInfo(false,"yscoco","yscoco");
        config.setBleLog(true,"yscoco");
        config.setScanBleLog(true);
        BleManage.getInstance().init(this,config);
    }
    public static SingleBleDriver getDriver(){
        return BleManage.getInstance().getMySingleDriver();
    }
    /*关闭log*/
    private void closeLog(boolean isOpen) {
        LogUtils.setLog(isOpen);/*关闭常用Log日志，false为关闭，true为开启*/
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }


}
