package com.yscoco.blue;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.yscoco.blue.imp.MoreBleDriver;
import com.yscoco.blue.imp.ScannerDriver;
import com.yscoco.blue.imp.SingleBleDriver;
import com.yscoco.blue.utils.BleStatusUtil;
import com.yscoco.blue.utils.FileWriteUtils;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/9 0009
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：BLE扫描类
 */
public class BleManage {
    private static BleManage mBleManage;
    private Application mContext;
    private BleConfig bleConfig = new BleConfig();
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    boolean isScanner = false;/*蓝牙是否正在扫描*/
    public BleManage() {
    }
    public static BleManage getInstance() {
        if (mBleManage==null) {
            synchronized (BleManage.class){
                if (mBleManage==null) {
                    mBleManage = new BleManage();
                }
            }
        }
        return mBleManage;
    }
    public void init(Application application,BleConfig config){
        mContext = application;
        if (isSupportBle()) {
            bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        if(config!=null){
            bleConfig = config;
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        FileWriteUtils.deleteFile();
//        BleStatusUtil.closeAndroidPDialog();
    }

    public MoreBleDriver getMyMoreDriver() {
        return MyMoreBleDriver.getInstance(mContext);
    }
    public SingleBleDriver getMySingleDriver(){
        return MySingleBleDriver.getInstance(mContext);
    }
    public ScannerDriver getMyBleScannerDriver() {
        return MyScannerDriver.getInstance(this);
    }
    /**
     * is support ble?
     *
     * @return
     */
    public boolean isSupportBle() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && mContext.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public boolean isEnableBluetooth() {
        if(bluetoothAdapter!=null){
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }
    /**
     * Open bluetooth
     */
    public void enableBluetooth() {
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
    }
    /**
     * Disable bluetooth
     */
    public void disableBluetooth() {
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled())
                bluetoothAdapter.disable();
        }
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }

    public void setBluetoothManager(BluetoothManager bluetoothManager) {
        this.bluetoothManager = bluetoothManager;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public boolean isScanner() {
        return isScanner;
    }

    public void setScanner(boolean scanner) {
        isScanner = scanner;
    }

    public BleConfig getBleConfig() {
        return bleConfig;
    }
}
