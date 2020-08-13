package com.yscoco.blue.base;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.yscoco.blue.MyBtManager;
import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.imp.MoreBleDriver;
import com.yscoco.blue.utils.LogBlueUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
/**
 * 作者：karl.wei
 * 创建日期： 2017/12/09
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：设备管理基类
 */

public abstract class BaseMoreBleDriver implements MoreBleDriver,HandleDriver {
    protected Context mContext;
    /*存储设备mac和设备管理类的关联*/
    protected Map<String, MyBtManager> mBtManagerMaps = new HashMap<>();
    protected Set<String> mReconnectSet = new HashSet<String>();
    public BaseMoreBleDriver() {
    }
    public BaseMoreBleDriver(Context c) {
        mContext = c;
    }
    @Override
    public boolean connect(String mac, BluetoothDevice mDevice, boolean isReconnect) {
        LogBlueUtils.e("连接中：" + mac+mBtManagerMaps.size());
        //管理单个连接
        MyBtManager btManager = mBtManagerMaps.get(mac);
        if (btManager == null) {
            btManager = new MyBtManager(mContext, mac,mDevice, this);
            mBtManagerMaps.put(mac, btManager);
        }else{
            LogBlueUtils.e("有设备处于连接状态数据");
        }
        btManager.setReconnect(isReconnect);
        return btManager.connected();
    }

    @Override
    public DeviceState getDeviceState(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
            return mBtManagerMaps.get(mac).getDeviceState();
        }
        return DeviceState.UNKNOW;
    }
    public boolean isDisConnect(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
            return mBtManagerMaps.get(mac).isDisconnected();
        }
        return false;
    }

    @Override
    public void disConnect(String mac, boolean isReconnect) {
        if (mBtManagerMaps.get(mac) != null) {
            LogBlueUtils.e("bluedisConnect"+mBtManagerMaps.size());
            mBtManagerMaps.get(mac).disConnect(mac,isReconnect);
        }
    }

    @Override
    public void disConnectAll(final String mac) {
        LogBlueUtils.e("断开全部的方法是"+mBtManagerMaps.size());
        for (String key : mBtManagerMaps.keySet()) {
            Log.e("blue","bluedisConnectAll");
            if(mac!= null&&key.equals(mac)){
                Log.e("blue","diconnect"+mac);
                continue;
            }else{
                mBtManagerMaps.get(key).disConnect(mBtManagerMaps.get(key).getmMac(),false);
            }
        }
        mReconnectSet.clear();
    }

    @Override
    public void setReconnect(String mac, boolean isReconnect) {
        if (mBtManagerMaps.get(mac) != null) {
            mBtManagerMaps.get(mac).setReconnect(isReconnect);
        }
    }

    @Override
    public boolean isReconnect(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
            return mBtManagerMaps.get(mac).isReconnect();
        }
        return false;
    }
    @Override
    public ArrayList<BluetoothDevice> getConnectDevice() {
        ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
        if(mBtManagerMaps!=null){
            for (String key : mBtManagerMaps.keySet()) {
                if(getDeviceState(key)==DeviceState.CONNECT||getDeviceState(key)==DeviceState.CONNECTING){
                    deviceList.add(mBtManagerMaps.get(key).getmDevice());
                }
            }
        }
        return deviceList;
    }
    @Override
    public void startReadRssi(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
//            mBtManagerMaps.get(mac).startReadRssi();
        }
    }

    @Override
    public void stopReadRssi(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
//            mBtManagerMaps.get(mac).stopReadRssi();
        }
    }


    @Override
    public boolean writeData(String mac, byte[] cmd) {
        if(mac==null){
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.writeData(cmd);
            }
        }else {
            if (mBtManagerMaps.get(mac) != null) {
                return mBtManagerMaps.get(mac).writeData(cmd);
            }
        }
        return true;
    }

    @Override
    public boolean writeData(String mac, byte[] cmd, String serviceUUID, String charUUID) {
        if(mac==null){
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.writeData(cmd);
            }
        }else {
            if (mBtManagerMaps.get(mac) != null) {
                return mBtManagerMaps.get(mac).writeData(cmd, serviceUUID, charUUID);
            }
        }
        return true;
    }


    @Override
    public boolean writeData(String mac, byte[] cmd,int type) {
        if(mac==null){
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.writeData(cmd,type);
            }
        }else {
            if (mBtManagerMaps.get(mac) != null) {
                return mBtManagerMaps.get(mac).writeData(cmd,type);
            }
        }
        return true;
    }

    @Override
    public boolean writeData(String mac, byte[] cmd, String serviceUUID, String charUUID,int type) {
        if(mac==null){
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.writeData(cmd,type);
            }
        }else {
            if (mBtManagerMaps.get(mac) != null) {
                return mBtManagerMaps.get(mac).writeData(cmd, serviceUUID, charUUID,type);
            }
        }
        return true;
    }
    @Override
    public void readData(String mac) {
        if (mBtManagerMaps.get(mac) != null) {
             mBtManagerMaps.get(mac).readData();
        }else{
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.readData();
            }
        }
    }

    @Override
    public void readData(String mac, String serviceUUID, String charUUID) {
        if (mBtManagerMaps.get(mac) != null) {
            mBtManagerMaps.get(mac).readData(serviceUUID,charUUID);
        }else{
            for(MyBtManager btManager:mBtManagerMaps.values()){
                btManager.readData(serviceUUID,charUUID);
            }
        }
    }
    public int getConnectSize(){
        return mBtManagerMaps==null? 0: mBtManagerMaps.size();
    }

    public Set<String> getmReconnectSet() {
        return mReconnectSet;
    }

    public void setmReconnectSet(Set<String> mReconnectSet) {
        this.mReconnectSet = mReconnectSet;
    }

    @Override
    public Set<String> getReconnect() {
        return mReconnectSet;
    }

    @Override
    public void addReconnect(String mac) {
        mReconnectSet.add(mac);
    }

    @Override
    public void removeReconnect(String mac) {
        mReconnectSet.remove(mac);
    }

    @Override
    public boolean isReconnectList(String mac) {
        return mReconnectSet.contains(mac);
    }

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String mac = msg.getData().getString("mac");
            handlerMsg(mac, msg);
        }
    };
    @Override
    public void sendMessage(String mac, int what) {
        sendMessage(mac, what, null);
    }
    @Override
    public void sendMessage(String mac, int what, Object data) {
        Message msg = new Message();
        msg.what = what;
        Bundle bundle = new Bundle();
        bundle.putString("mac", mac);
        msg.setData(bundle);
        if (data != null) {
            msg.obj = data;
        }
        mHandler.sendMessage(msg);
    }
}

