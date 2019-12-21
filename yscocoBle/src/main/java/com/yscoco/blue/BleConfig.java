package com.yscoco.blue;

import com.yscoco.blue.bean.NotifyUUIDBean;
import com.yscoco.blue.utils.BleScanUtils;
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
     * 默认 service UUID
     */
    public String SERVICE_UUID1 = "0000FFF0-0000-1000-8000-00805F9B34FB";
    /**
     * 默认 notify UUID
     */
    public String CHA_NOTIFY = "0000FFF4-0000-1000-8000-00805F9B34FB";
    /**
     *默认 Write  UUID
     */
    public String CHA_WRITE = "0000FFF4-0000-1000-8000-00805F9B34FB";
    /**
     * 本地日志文件名称
     */
    public String PROJECT_NAME = "yscoco";
    /**
     * 是否关闭本地文件日志
     */
    public boolean isCloseFile = false;
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
     * 当Notify只有一个UUID时可以使用此构造方法
     * @param SERVICE_UUID1 服务UUID
     * @param    CHA_NOTIFY Notify的UUID
     * @param    CHA_WRITE  默认写入数据的UUID
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

    /**
     * 设备连接成功后需要同时开启的Notify 配置
     */
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
    /**
     * 设置是否存储BLE 通讯过程数据
     * @param isCloseFile 是否关闭本地日志存储，true 关闭 false 打开
     * @param fileName 文件名称 ，存储文件统一存储在yscoco文件下，格式 file_YYYY-MM-DD.txt
     */
    public void setFileInfo(boolean isCloseFile,String fileName){
        setCloseFile(isCloseFile);
        setPROJECT_NAME(fileName);
    }
    /**
     * 设置是否打印日志
     * @param isLog 是否显示日志，true 显示 false 不显示
     * @param LogStart 日志开头的标识
     */
    public void setBleLog(boolean isLog,String LogStart){
        LogBlueUtils.setLog(isLog,LogStart);/*设置BLe状态*/
    }
    /**
     * 扫描的设备信息是否需要显示Log（多连接时日志过多导致数据不好筛选所做）
     * @param isLog true为显示扫描到的设备信息，false 不显示扫描获取到的设备信息
     */
    public void setScanBleLog(boolean isLog){
        BleScanUtils.isLog(isLog);
    }
}
