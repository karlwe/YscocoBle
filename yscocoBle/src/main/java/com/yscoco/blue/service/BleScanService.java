package com.yscoco.blue.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.yscoco.blue.BleManage;
import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.listener.BleStateListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：karl.wei
 * 创建日期： 2018/7/4 0004
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙扫描
 */
public class BleScanService extends Service implements BleStateListener {
    private BluetoothAdapter mBluetoothAdapter;// 蓝牙适配器
    TimerTask blueTask;
    Timer blueTimer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected void init() {
        // 初始化蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        BleManage.getInstance().getMyMoreDriver().addBleStateListener(this);
        initTask();
    }
    private void initTask() {
        // 增加读rssi 的定时器F
        blueTask = new TimerTask() {
            @Override
            public void run() {
                if(BleManage.getInstance().getMyMoreDriver().getReconnect().size()!=0) {
                    Log.e("blue","开始搜索");
                    scanLeDevice(true);
                }
            }
        };
        blueTimer = new Timer();
        if (blueTask != null) {
            blueTimer.schedule(blueTask, 1000, 12000);
        }
    }
    // 搜索设备是耗时操作 放在异步线程中
    @SuppressLint("NewApi")
    public void scanLeDevice(final boolean enable) {
        if(mBluetoothAdapter.enable()) {
            if (enable) {
                Log.e("blue","startLeScan");
                mBluetoothAdapter.startLeScan(mLeScanCallback);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("blue","stopLeScan");
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    }
                }, 10000);
            } else {
                if (mBluetoothAdapter != null) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            Log.e("blue","搜索到设备"+device.getAddress());
            scan(device);
        }
    };
    public void scan(BluetoothDevice device){
        if(BleManage.getInstance().getMyMoreDriver().isReconnectList(device.getAddress())){
            BleManage.getInstance().getMyMoreDriver().removeReconnect(device.getAddress());
            scanLeDevice(false);
            BleManage.getInstance().getMyMoreDriver().connect(device.getAddress(),device,true);
        }
    };

    public void onDestroy() {
        super.onDestroy();
        Log.e("blue","开始搜索");
        scanLeDevice(false);
        BleManage.getInstance().getMyMoreDriver().removeBleStateListener(this);
        BleManage.getInstance().getMyMoreDriver().getReconnect().clear();
        BleManage.getInstance().getMyMoreDriver().disConnectAll(null);
    }
    @Override
    public void reConnected(String mac) {
        Log.e("blue","有意外断连");
        if(BleManage.getInstance().getMyMoreDriver().getReconnect().size()!=0) {
            Log.e("blue","开始搜索");
            scanLeDevice(true);
        }
    }

    @Override
    public void deviceStateChange(String mac, DeviceState state) {
        if(state==DeviceState.CONNECT){
            if(BleManage.getInstance().getMyMoreDriver().getReconnect().size()!=0) {
                Log.e("blue","开始搜索");
                scanLeDevice(true);
            }else{
                scanLeDevice(false);
            }
        }
    }

    @Override
    public void onNotifySuccess(String mac) {

    }
}
