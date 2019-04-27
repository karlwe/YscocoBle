package com.yscoco.blue.bean;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/09
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙连接实体类
 */
public class BlueDevice implements Serializable{
    private BluetoothDevice device;
    private int rssi;
    private byte[] scanByte;

    public BlueDevice(BluetoothDevice device, int rssi, byte[] scanByte) {
        this.device = device;
        this.rssi = rssi;
        this.scanByte = scanByte;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte[] getScanByte() {
        return scanByte;
    }

    public void setScanByte(byte[] scanByte) {
        this.scanByte = scanByte;
    }

    @Override
    public String toString() {
        return "BlueDevice{" +
                "device=" + device +
                ", rssi=" + rssi +
                ", scanByte=" + Arrays.toString(scanByte) +
                '}';
    }
}
