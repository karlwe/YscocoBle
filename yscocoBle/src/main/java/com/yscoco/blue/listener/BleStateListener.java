package com.yscoco.blue.listener;

import com.yscoco.blue.enums.DeviceState;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/09
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙连接状态回调
 */

public interface BleStateListener {
    /**
     * 设备状态改变
     * @param mac
     */
    void deviceStateChange(String mac,DeviceState state);
    /**
     * 发现服务
     * @param mac
     */
    void onNotifySuccess(String mac);

    /**
     * 重连机制
     * @param mac
     */
    void reConnected(String mac);
}
