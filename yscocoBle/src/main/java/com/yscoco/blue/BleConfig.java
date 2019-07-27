package com.yscoco.blue;

import com.yscoco.blue.bean.NotifyUUIDBean;
import com.yscoco.blue.utils.LogBlueUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/12 0012
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：配置文件
 */
public class BleConfig {
    /**
     * service UUID
     */
    public String SERVICE_UUID1 = "0000FFF0-0000-1000-8000-00805F9B34FB";
    /**
     * 通知 UUID
     */
    public String CHA_NOTIFY = "0000FFF4-0000-1000-8000-00805F9B34FB";
    /**
     *写数据 UUID
     */
    public String CHA_WRITE = "0000FFF4-0000-1000-8000-00805F9B34FB";
    public String PROJECT_NAME = "yscoco";
    public boolean isCloseFile = false;/*是否关闭本地文件日志*/
    public List<NotifyUUIDBean> notifyList = new ArrayList<NotifyUUIDBean>();
    /**
     *多Notify硬件配置构造方法，需要额外配置主通知和回调接口
     *
     */
    public BleConfig() {
        notifyList.clear();
        notifyList.add(new NotifyUUIDBean(SERVICE_UUID1,CHA_NOTIFY));
    }
    /**
     *单Notify硬件配置构造方法
     */
    public BleConfig(String SERVICE_UUID1, String CHA_NOTIFY, String CHA_WRITE) {
        this.SERVICE_UUID1 = SERVICE_UUID1;
        this.CHA_NOTIFY = CHA_NOTIFY;
        this.CHA_WRITE = CHA_WRITE;
        notifyList.clear();
        notifyList.add(new NotifyUUIDBean(SERVICE_UUID1,CHA_NOTIFY));
    }
    public String getSERVICE_UUID1() {
        return SERVICE_UUID1;
    }

    public void setSERVICE_UUID1(String SERVICE_UUID1) {
        this.SERVICE_UUID1 = SERVICE_UUID1;
    }

    public String getCHA_NOTIFY() {
        return CHA_NOTIFY;
    }

    public void setCHA_NOTIFY(String CHA_NOTIFY) {
        this.CHA_NOTIFY = CHA_NOTIFY;
    }

    public String getCHA_WRITE() {
        return CHA_WRITE;
    }

    public void setCHA_WRITE(String CHA_WRITE) {
        this.CHA_WRITE = CHA_WRITE;
    }
    public List<NotifyUUIDBean> getNotifyList() {
        return notifyList;
    }

    public void setNotifyList(List<NotifyUUIDBean> notifyList) {
        this.notifyList = notifyList;
    }

    public String getPROJECT_NAME() {
        return PROJECT_NAME;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public boolean isCloseFile() {
        return isCloseFile;
    }

    public void setCloseFile(boolean closeFile) {
        isCloseFile = closeFile;
    }
    public void setBleLog(boolean isLog,String LogStart){
        LogBlueUtils.setLog(isLog,LogStart);/*设置BLe状态*/
    }
}
