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
import com.yscoco.blue.imp.SingleBleDriver;

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

public abstract class BaseSingleBleDriver implements SingleBleDriver,HandleDriver {
    protected Context mContext;
    /*存储设备mac和设备管理类的关联*/
    protected MyBtManager mBtManager= null;
    protected boolean isFinish = false;
    protected Set<String> mReconnectSet = new HashSet<String>();
    protected Map<String,BluetoothDevice> deviceMap = new HashMap<>();
    public BaseSingleBleDriver() {
    }
    public BaseSingleBleDriver(Context c) {
        mContext = c;
    }
    @Override
    public boolean connect(String mac,BluetoothDevice mDevice,boolean isReconnect) {
        Log.e("blue","连接中：" + mac+mBtManager);
        //管理单个连接
        if (mBtManager != null) {
            if(mBtManager.getmDevice().getAddress().equals(mac)){
                mBtManager.setReconnect(isReconnect);
                return true;
            }else{
                mBtManager.disConnect(mBtManager.getmMac(),false);
            }
        }
        deviceMap.put(mac,mDevice);
        mBtManager = new MyBtManager(mContext, mac,mDevice, this);
        mBtManager.setReconnect(isReconnect);
        return mBtManager.connected();
    }

    @Override
    public DeviceState getDeviceState() {
        if (mBtManager!=null) {
            return mBtManager.getDeviceState();
        }
        return DeviceState.UNKNOW;
    }
    @Override
    public void disConnect(boolean isReconnect) {
        if (mBtManager != null) {
            Log.e("blue","bluedisConnect"+mBtManager);
            mBtManager.disConnect(mBtManager.getmMac(),isReconnect);
        }
    }

    @Override
    public void setReconnect(boolean isReconnect) {
        if (mBtManager != null) {
            mBtManager.setReconnect(isReconnect);
        }
    }

    @Override
    public boolean isReconnect() {
        if (mBtManager != null) {
            return mBtManager.isReconnect();
        }
        return false;
    }
    @Override
    public BluetoothDevice getConnectDevice() {
        if(mBtManager!=null){
            return mBtManager.getmDevice();
        }
        return null;
    }
    @Override
    public void startReadRssi() {
        if (mBtManager != null) {
//            mBtManagerMaps.get(mac).startReadRssi();
        }
    }

    @Override
    public void stopReadRssi() {
        if (mBtManager != null) {
//            mBtManagerMaps.get(mac).stopReadRssi();
        }
    }


    @Override
    public boolean writeData(byte[] cmd) {
            if (mBtManager != null) {
                return mBtManager.writeData(cmd);
            }
        return false;
    }

    @Override
    public boolean writeData(byte[] cmd, String serviceUUID, String charUUID) {
            if (mBtManager != null) {
                return mBtManager.writeData(cmd, serviceUUID, charUUID);
            }
        return true;
    }


    @Override
    public boolean writeData(byte[] cmd,int type) {
        if (mBtManager != null) {
            return mBtManager.writeData(cmd);
        }
        return false;
    }

    @Override
    public boolean writeData(byte[] cmd, String serviceUUID, String charUUID,int type) {
        if (mBtManager != null) {
            return mBtManager.writeData(cmd, serviceUUID, charUUID);
        }
        return true;
    }
    @Override
    public void readData() {
        if (mBtManager != null) {
            mBtManager.readData();
        }
    }

    @Override
    public void readData( String serviceUUID, String charUUID) {
        if (mBtManager != null) {
            mBtManager.readData(serviceUUID,charUUID);
        }
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

    @Override
    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public BluetoothDevice getDevice(String mac) {
        return deviceMap.get(mac);
    }

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String mac = msg.getData().getString("mac");
            handlerMsg(mac,msg);
        }
    };

    @Override
    public void sendMessage(String mac, int what) {
        sendMessage(mac,what, null);
    }
    @Override
    public void sendMessage(String mac,int what, Object data) {
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

