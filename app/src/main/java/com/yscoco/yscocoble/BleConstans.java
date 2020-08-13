package com.yscoco.yscocoble;

/**
 * 作者：karl.wei
 * 创建日期： 2018/12/3 0003
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：配置参数
 */
public class BleConstans {
    // service
    public static final String SERVICE_UUID1 = "6E400001-B5A3-F393-E0A9-E50E24DCCA9F";
    //通知
    public static final String CHA_NOTIFY = "6E400003-B5A3-F393-E0A9-E50E24DCCA9F";
    //写数据
    public static final String CHA_WRITE = "6E400002-B5A3-F393-E0A9-E50E24DCCA9F";

    public static boolean isOpenReconnectScanner = true;/*是否开启重连扫描*/
    // 电量service
    public static final String SERVICE_BATTERY_UUID = "0000180F-0000-1000-8000-00805F9B34FB";
    //电量通知
    public static final String CHA_BATTERY_NOTIFY = "00002A19-0000-1000-8000-00805F9B34FB";
    public static final String RECONNECT_SCANNER_STATE_CHANGLE = "com.yscoco.ble.RECONNECT_SCANNER_STATE_CHANGLE";/*重连状态改变*/
    // 软件service
    public static final String SERVICE_VESION_UUID = "0000180A-0000-1000-8000-00805F9B34FB";
    //软件vision
    public static final String CHA_VISION_READ = "00002A27-0000-1000-8000-00805F9B34FB";
    //firmware revision
    public static final String CHA_FIRMWARE_VISION_READ = "00002A26-0000-1000-8000-00805F9B34FB";

    public static final String SERVICE_OTA_UUID = "6E40FF01-B5A3-F393-E0A9-E50E24DCCA9E";
    public static final String CHA_OTA_WRITE = "6E40FF02-B5A3-F393-E0A9-E50E24DCCA9E";
    public static int battery = 100;
}
