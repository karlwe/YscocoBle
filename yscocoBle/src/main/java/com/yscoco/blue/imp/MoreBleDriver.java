package com.yscoco.blue.imp;

import android.bluetooth.BluetoothDevice;

import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.listener.BleStateListener;
import com.yscoco.blue.listener.BleDataListener;

import java.util.ArrayList;
import java.util.Set;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/09
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：设备管理基类
 */

public interface MoreBleDriver {
    /**
     * 连接设备
     * @param mac
     * @return
     */
    boolean connect(String mac, BluetoothDevice mDevice, boolean isReconnect);
    /**
     * 设备是否连接
     * @param mac
     * @return
     */
    DeviceState getDeviceState(String mac);
    /**
     * 设备是否断开连接过
     * @param mac
     * @return
     */
    boolean isDisConnect(String mac);
    /**
     * 断开连接的设备
     * @param mac
     * @param isReconnect  是否重连，不重连则会移除该设备的重连信息
     */
    void disConnect(String mac, boolean isReconnect);
    /**
     * 获取当前连接的设备
     */
    ArrayList<BluetoothDevice> getConnectDevice();
    /**
     * 断开所有连接的设备
     * @param mac 忽略掉的设备，为空代表不忽略
     */
    void disConnectAll(String mac);

    /**
     * 设置设备是否重连，主动断开连接或者移除设备前需要设置
     * @param mac
     * @param isReconnect 是否重连
     */
    void setReconnect(String mac, boolean isReconnect);

    /**
     * 设备是否在重连列表，扫描到设备后判断是否发起重连
     * @param mac
     */
    boolean isReconnect(String mac);

    void addBleStateListener(BleStateListener listener);
    void removeBleStateListener(BleStateListener listener);
     void addBleDataListener(BleDataListener listener);
    void removeBleDataListener(BleDataListener listener);
    /**
     * 读取RSSI值
     */
    void startReadRssi(String mac);
    /**
     * 停止读取RSSI值
     */
    void stopReadRssi(String mac);
    /**
     * 发送数据
     */
    boolean writeData(String mac, byte[] cmd);
    /**
     * 发送特定服务和属性的数据
    */
    boolean writeData(String mac, byte[] cmd, String serviceUUID, String charUUID);

    /**
     * 发送数据
     * @param writeType 数据写入类型
     */
    boolean writeData(String mac, byte[] cmd,int writeType);
    /**
     * 发送特定服务和属性的数据
     * @param writeType
     */
    boolean writeData(String mac, byte[] cmd, String serviceUUID, String charUUID,int writeType);
    /**
     * 读取数据
     */
    void readData(String mac);
    /**
     * 读取特定服务和属性的数据
     */
    void readData(String mac, String serviceUUID, String charUUID);
    /**
     * 获取当前连接的设备列表
     */
    int getConnectSize();
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
}
