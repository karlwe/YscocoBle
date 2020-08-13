package com.yscoco.yscocoble.sharedpreferences;

import java.io.Serializable;

/**
 * 作者：karl.wei
 * 创建日期： 2018/10/22 0022
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：设备列表
 */
public class DeviceBean implements Serializable{
    private String mMac;
    private String mName;
    private boolean isBandle = false;

    public DeviceBean() {
    }

    public DeviceBean(String mMac, String mName) {
        this.mMac = mMac;
        this.mName = mName;
    }

    public String getmMac() {
        return mMac;
    }

    public void setmMac(String mMac) {
        this.mMac = mMac;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean isBandle() {
        return isBandle;
    }

    public void setBandle(boolean bandle) {
        isBandle = bandle;
    }
}
