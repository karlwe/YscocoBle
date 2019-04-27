package com.yscoco.blue.utils;/**
 * Created by Administrator on 2018/1/6 0006.
 */

import com.yscoco.blue.listener.BleDataListener;

import java.util.Set;

/**
 * 作者：karl.wei
 * 创建日期： 2018/1/6 0006 16:54
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：处理notify返回的数据结果
 */
public class BleDataUtils {
    public static void notify(String uuid, String mac,byte[] data, Set<BleDataListener> mNotifyListener){
        StringBuffer b = new StringBuffer();
        for (byte bytes:data) {
            b.append(String.format("%02X ", bytes).toString().trim());
        }
        LogBlueUtils.d(b.toString()+"mNotifyListener.size"+mNotifyListener.size());
        FileWriteUtils.initWrite(b.toString()+"mNotifyListener.size"+mNotifyListener.size());
        for (BleDataListener listner : mNotifyListener) {
            listner.notify(mac,uuid.toUpperCase(),data);
        }
    }

    public static void read(String uuid, String mac,byte[] data, Set<BleDataListener> mNotifyListener){
        StringBuffer b = new StringBuffer();
        for (byte bytes:data) {
            b.append(String.format("%02X ", bytes).toString().trim());
        }
        LogBlueUtils.d(b.toString()+"BleDataListener.size"+mNotifyListener.size());
        FileWriteUtils.initWrite(b.toString()+"BleDataListener.size"+mNotifyListener.size());
        for (BleDataListener listner : mNotifyListener) {
            listner.read(mac,uuid.toUpperCase(),data);
        }
    }
}
