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
    public static final String SERVICE_UUID1 = "0000FFF0-0000-1000-8000-00805F9B34FB";
    //通知
    public static final String CHA_NOTIFY = "0000FFF4-0000-1000-8000-00805F9B34FB";
    //写数据
    public static final String CHA_WRITE = "0000FFF4-0000-1000-8000-00805F9B34FB";


    // 电量service
    public static final String SERVICE_BATTERY_UUID = "0000180F-0000-1000-8000-00805F9B34FB";
    //电量通知
    public static final String CHA_BATTERY_NOTIFY = "00002A19-0000-1000-8000-00805F9B34FB";
    public static boolean isOpenReconnectScanner = true;/*是否开启重连扫描*/
    public static final String RECONNECT_SCANNER_STATE_CHANGLE = "com.yscoco.ble.RECONNECT_SCANNER_STATE_CHANGLE";/*重连状态改变*/

}
