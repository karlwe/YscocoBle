package com.yscoco.yscocoble;

import android.graphics.Typeface;
import android.util.DisplayMetrics;

import com.yscoco.blue.BleConfig;
import com.yscoco.blue.BleManage;
import com.yscoco.blue.app.BaseBlueApplication;
import com.yscoco.blue.base.BaseMoreBleDriver;
import com.yscoco.blue.bean.NotifyUUIDBean;
import com.yscoco.blue.imp.MoreBleDriver;
import com.yscoco.blue.imp.SingleBleDriver;
import com.yscoco.blue.utils.FileWriteUtils;
import com.yscoco.blue.utils.LogBlueUtils;

import java.lang.reflect.Field;
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
        initBle();
        setSize();
    }
    private void setSize() {
        Typeface mTypeface = Typeface.createFromAsset(getAssets(), "fonts/PingFang Regular_0.ttf");

        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            field.set(null, mTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void initBle() {
        BleConfig config = new BleConfig();
        config.SERVICE_UUID1 = BleConstans.SERVICE_UUID1;
        config.CHA_NOTIFY =    BleConstans.CHA_NOTIFY;
        config.CHA_WRITE =     BleConstans.CHA_WRITE;
        List<NotifyUUIDBean> beansList = new ArrayList<NotifyUUIDBean>();
        beansList.add(new NotifyUUIDBean(BleConstans.SERVICE_UUID1,BleConstans.CHA_NOTIFY));
        beansList.add(new NotifyUUIDBean(BleConstans.SERVICE_BATTERY_UUID,BleConstans.CHA_BATTERY_NOTIFY));
        config.setNotifyList(beansList);
        config.setPROJECT_NAME("yscoco");//本地log日志名称
        config.setCloseFile(false);//是否开启写入本地log日志
        config.setBleLog(true,"BLE:");//控制台显示打印
        BleManage.getInstance().init(this,config);
    }

    public static MoreBleDriver getBleDriver() {
        return BleManage.getInstance().getMyMoreDriver();
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
