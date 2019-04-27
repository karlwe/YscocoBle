package com.yscoco.blue.base;/**
 * Created by Administrator on 2017/12/9 0009.
 */

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/9 0009 11:15
 * 邮箱：karl.wei@yscoco.com
 *QQ:2736764338
 * 类介绍：蓝牙的常量工具
 */
public class BaseBtManager {
    /**
     * 开启通知UUID
     */
    public static final String DES_UUID1 = "00002902-0000-1000-8000-00805F9B34FB";

    /**
     * 设备连接成功
     */
    public static final int CONNECTED = 1001;

    /**
     * 设备断开(设备报警之后的状态，此时调整UI)
     */
    public static final int DISCONNECT = 1002;

    /**
     * 设备连接中
     */
    public static final int CONNECTING = 1003;

    /**
     * 设备断开连接中
     */
    public static final int DISCONNECTING = 1004;

    /**
     * 通道开启成功
     */
    public static final int NOTIFY_ON = 1005;

    /**
     * 重连
     */
    public static final int RE_CONNECT = 1006;

    /**
     * 通知数据
     */
    public static final int NOTIFY_VALUE = 1007;

    /**
     * 通知RSSI变化
     */
    public static final int NOTIFY_RSSI = 108;
    /**
     * 读取数据
     */
    public static final int READ_VALUE = 109;
}
