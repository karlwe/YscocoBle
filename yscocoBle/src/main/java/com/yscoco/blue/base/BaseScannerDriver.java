package com.yscoco.blue.base;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yscoco.blue.BleManage;
import com.yscoco.blue.bean.BlueDevice;
import com.yscoco.blue.enums.BleScannerState;
import com.yscoco.blue.enums.ScanNameType;
import com.yscoco.blue.imp.ScannerDriver;
import com.yscoco.blue.utils.FileWriteUtils;
import com.yscoco.blue.utils.LogBlueUtils;

import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.le.ScanSettings.SCAN_MODE_BALANCED;
import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;
import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_POWER;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/10 0010
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙搜索基类
 */
public abstract class BaseScannerDriver implements ScannerDriver {
    protected BleManage bleManage;
    private boolean isScan = false;
    private String scanName;/**/
    private ScanNameType scanType = ScanNameType.ALL;
    ScanSettings scanSettings = null;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseScannerDriver(BleManage bleManage) {
        this.bleManage = bleManage;
        if (scanSettings == null) {
            ScanSettings.Builder builder = new ScanSettings.Builder();
            builder.setScanMode(SCAN_MODE_LOW_LATENCY);
            scanSettings = builder.build();
        }
    }
    @Override
    public boolean isScanner() {
        return isScan;
    }
    @Override
    public void scan(String name, ScanNameType type) {
        this.scanName = name ;
        this.scanType = type;
        if(isScan){
            FileWriteUtils.initWrite("ScanCallback：蓝牙广播已经开启扫描");
            LogBlueUtils.d("已经开启扫描");
            return ;
        }
        if(!bleManage.isEnableBluetooth()){
            bleManage.enableBluetooth();
            FileWriteUtils.initWrite("ScanCallback：蓝牙开关状态关闭，重启中");
            return ;
        }
        isScan = true;
        scanState();
        if (Build.VERSION.SDK_INT < 21) {
            bleManage.getBluetoothAdapter().startLeScan(callBack43);
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描监听回调callBack43");
        }else{
            List<ScanFilter> filters = new ArrayList<>();
            filters.add(new ScanFilter.Builder().build());
            bleManage.getBluetoothAdapter().getBluetoothLeScanner().startScan(filters,scanSettings,callBack50);
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描监听回调callBack50");
        }
    }

    @Override
    public void stop() {
        if(!isScan){
            LogBlueUtils.d("ScanCallback:未开启扫描");
            FileWriteUtils.initWrite("ScanCallback：蓝牙未开启扫描");
            return ;
        }
        isScan = false;
        scanState();
        if (Build.VERSION.SDK_INT < 21) {
            if(bleManage!=null&&bleManage.getBluetoothAdapter()!=null&&callBack43!=null){
                bleManage.getBluetoothAdapter().stopLeScan(callBack43);
            }
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描监听回调停止callBack43");
        }else{
            if(bleManage!=null&&bleManage.getBluetoothAdapter()!=null&&bleManage.getBluetoothAdapter().getBluetoothLeScanner()!=null&&callBack50!=null) {
                bleManage.getBluetoothAdapter().getBluetoothLeScanner().stopScan(callBack50);
            }
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描监听回调停止callBack50");
        }
    }
    protected void onScan(BluetoothDevice device, byte[] scan,int rssi){
        String deviceName = device.getName();
        switch (scanType){
            case ALL:
                if(scanName!=null&&!deviceName.equals(scanName)){
                    return;
                }
                break;
            case START:
                if(scanName!=null&&!deviceName.startsWith(scanName)){
                    return;
                }
                break;
            case END:
                if(scanName!=null&&!deviceName.endsWith(scanName)){
                    return;
                }
                break;
            case MATCHING:
                if(scanName!=null&&!deviceName.contains(scanName)){
                    return;
                }
                break;
        }
        Message msg = new Message();
        msg.what = 1;
        msg.obj = new BlueDevice(device,rssi,scan);
        mHandler.sendMessage(msg);
    };
    private void scanState(){
        Message msg = new Message();
        msg.what = 2;
        mHandler.sendMessage(msg);
    };
    public abstract void handlerMsg(BlueDevice device);
    public abstract void handlerMsg(BleScannerState state);
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    handlerMsg((BlueDevice)msg.obj);
                    break;
                case 2:
                    if(isScan){
                        handlerMsg(BleScannerState.OPEN_SCANNER);
                    }else{
                        handlerMsg(BleScannerState.CLOSE_SCANNER);
                    }
                    break;
                case 3:
                    stop();
                    break;
            }
        }
    };


    BluetoothAdapter.LeScanCallback callBack43 = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LogBlueUtils.i("ScanCallback:onLeScan:"+device.getName()+device.getAddress());
            if(device.getName()==null){
                return;
            }
            LogBlueUtils.d("ScanCallback:onLeScan:"+device.getName()+device.getAddress());
            onScan(device, scanRecord,rssi);
        }
    };

    ScanCallback callBack50 = new ScanCallback(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            byte[] scanRecord = result.getScanRecord().getBytes();
            int rssi = result.getRssi();
            if(device.getName()==null){
                return;
            }
            LogBlueUtils.d("ScanCallback:onScanResult"+device.getName()+device.getAddress());
            onScan(device, scanRecord,rssi);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描callback50 onScanFailed");
            LogBlueUtils.d("ScanCallback:onScanFailed,errorCode:"+errorCode);
            Message msg = new Message();
            msg.what = 3;
            mHandler.sendMessage(msg);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            FileWriteUtils.initWrite("ScanCallback：蓝牙扫描callback50 onBatchScanResults");
            LogBlueUtils.d("ScanCallback:onBatchScanResults");
        }
    };
}
