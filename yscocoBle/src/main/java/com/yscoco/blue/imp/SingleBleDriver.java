package com.yscoco.blue.imp;

import android.bluetooth.BluetoothDevice;

import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.listener.BleDataListener;
import com.yscoco.blue.listener.BleStateListener;

import java.util.Set;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/09
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：设备管理基类
 */

public interface SingleBleDriver {
    /**
     * 连接设备
     * @param mac
     * @return
     */
    boolean connect(String mac, BluetoothDevice mDevice, boolean isReconnect);
    /**
     * 设备连接状态
     * @return
     */
    DeviceState getDeviceState();
    /**
     * 断开连接的设备
     * @param isReconnect  是否重连
     */
    void disConnect(boolean isReconnect);
    BluetoothDevice getConnectDevice();
    /**
     * 设置设备是否重连
     * @param isReconnect 是否重连
     */
    void setReconnect(boolean isReconnect);

    boolean isReconnect();

    void addBleStateListener(BleStateListener listener);
    void removeBleStateListener(BleStateListener listener);
     void addBleDataListener(BleDataListener listener);
    void removeBleDataListener(BleDataListener listener);
    /**
     * 读取RSSI值
     */
    void startReadRssi();
    /**
     * 停止读取RSSI值
     */
    void stopReadRssi();
    /**
     * 发送数据
     */
    boolean writeData(byte[] cmd);
    /**
     * 发送特定服务和属性的数据
    */
    boolean writeData(byte[] cmd, String serviceUUID, String charUUID);


    /**
     * 发送数据
     * @param writeType  BluetoothGattCharacteristic.WRITE_...
     */
    boolean writeData(byte[] cmd,int writeType);
    /**
     * 发送特定服务和属性的数据
     * @param writeType  BluetoothGattCharacteristic.WRITE_...
     */
    boolean writeData(byte[] cmd, String serviceUUID, String charUUID,int writeType);

    /**
     * 读取数据
     */
    void readData();
    /**
     * 读取特定服务和属性的数据
     */
    void readData(String serviceUUID, String charUUID);
    /**
     * 获取需要回连的设备列表
     */
    Set<String> getReconnect();
    /**
     * 添加需要回连的设备列表
     */
    void addReconnect(String mac);
    /**
     * 移除需要回连的设备
     */
    void removeReconnect(String mac);
    /**
     * 判断当前设备是否在回连列表
     */
    boolean isReconnectList(String mac);

    /*设置应用是否处于销毁状态*/
    void setFinish(boolean isFinish);
}
