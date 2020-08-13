package com.yscoco.yscocoble.base.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;

import com.ys.module.log.LogUtils;
import com.yscoco.blue.BleManage;
import com.yscoco.blue.bean.BlueDevice;
import com.yscoco.blue.enums.BleScannerState;
import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.enums.ScanNameType;
import com.yscoco.blue.listener.BleScannerListener;
import com.yscoco.blue.listener.BleStateListener;
import com.yscoco.blue.utils.FileWriteUtils;
import com.yscoco.yscocoble.BleConstans;
import com.yscoco.yscocoble.MyApp;
import com.yscoco.yscocoble.sharedpreferences.DeviceBean;
import com.yscoco.yscocoble.sharedpreferences.SharePreferenceDevice;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：karl.wei
 * 创建日期： 2018/5/16 0016
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙断线重连基类Activity
 */

public abstract class BaseReconnectBlueActivity extends BaseActivity implements BleStateListener, BleScannerListener {
    TimerTask blueTask;
    Timer blueTimer;
    LocationManager lm;
    @Override
    protected void init() {
        // 初始化蓝牙适配器
        BleManage.getInstance().getMyBleScannerDriver().addBleScannerLister(this);
       MyApp.getDriver().addBleStateListener(this);
        DeviceBean device = SharePreferenceDevice.readShareDevice(BaseReconnectBlueActivity.this);
        if(device!=null){
           MyApp.getDriver().addReconnect(device.getmMac());
        }
        LogUtils.e("设备信息："+device);
        initlocaltion();
        initTask();
        initReceiver();
    }
    private void initlocaltion() {
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            FileWriteUtils.initWrite("开启定位以后进行的扫描：initlocaltion（）66"+toString());
        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter(BleConstans.RECONNECT_SCANNER_STATE_CHANGLE);
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myReceiver,filter);
    }
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case Intent.ACTION_SCREEN_ON:
                    scanLeDevice(false);
                    bleHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scanLeDevice(true);
                            FileWriteUtils.initWrite("开启屏幕亮屏开启的扫描：ACTION_SCREEN_ON（）93"+toString());
                        }
                    },2000);
                    break;
//                case Intent.ACTION_SCREEN_OFF:
//                    scanLeDevice(false);
//                    break;
                case BleConstans.RECONNECT_SCANNER_STATE_CHANGLE:
                    scanLeDevice(true);
                    FileWriteUtils.initWrite("重连状态改变开启的扫描：RECONNECT_SCANNER_STATE_CHANGLE（）102"+toString());
                    break;
                case LocationManager.PROVIDERS_CHANGED_ACTION:
                    boolean isEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(isEnabled){
                        scanLeDevice(true);
                        FileWriteUtils.initWrite("定位状态改变开启的扫描：PROVIDERS_CHANGED_ACTION（）108"+toString());
                    }else{
                        scanLeDevice(false);
                        FileWriteUtils.initWrite("定位状态改变开启开启的扫描：PROVIDERS_CHANGED_ACTION（）111"+toString());
                    }
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    switch (state){
                        case BluetoothAdapter.STATE_OFF:
                            scanLeDevice(false);
                            FileWriteUtils.initWrite("蓝牙开关状态关闭状态开启的扫描：ACTION_STATE_CHANGED（）120"+toString());
                            break;
                        case BluetoothAdapter.STATE_ON:
                            scanLeDevice(true);
                            FileWriteUtils.initWrite("蓝牙开关状态开启状态开启的扫描：ACTION_STATE_CHANGED（）124"+toString());
                            break;
                    }
                    break;
            }
        }
    };
    private void initTask() {
        // 增加读rssi 的定时器F
        blueTask = new TimerTask() {
            @Override
            public void run() {
                LogUtils.e("开始搜索");
                LogUtils.e("initTask:scanLeDevice(true)");
                scanLeDevice(true);
                FileWriteUtils.initWrite("定时器定时开启的扫描：initTask（）139"+toString());
            }
        };
        blueTimer = new Timer();
        if (blueTask != null) {
            blueTimer.schedule(blueTask, 1000, 21000);
        }
    }
    // 搜索设备是耗时操作 放在异步线程中
    @SuppressLint("NewApi")
    public void scanLeDevice(final boolean enable) {
        if(!BleConstans.isOpenReconnectScanner&&lm!=null){
            return;
        }
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!ok) {//开了定位服务
            return;
        }
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        boolean isScreenOn = pm.isScreenOn();
//        if(!isScreenOn){
//            return;
//        }
        if (enable) {
            LogUtils.e("开启了扫描");
            bleHandler.removeCallbacksAndMessages(null);
            if (BleManage.getInstance().getMySingleDriver().getReconnect().size() == 0) {
                DeviceBean deviceBean = SharePreferenceDevice.readShareDevice(BaseReconnectBlueActivity.this);
                if(deviceBean==null){
                    LogUtils.e("没有绑定过设备不需要重连");
                    return;
                }else{
                    if(BleManage.getInstance().getMySingleDriver().getDeviceState()== DeviceState.CONNECT|| BleManage.getInstance().getMySingleDriver().getDeviceState()== DeviceState.CONNECTING){
                        LogUtils.e("设备已连接，不需要重连");
                        return;
                    }else{
                       MyApp.getDriver().getReconnect().add(deviceBean.getmMac());
                    }

                }
            }
            BleManage.getInstance().getMyBleScannerDriver().scan(null, ScanNameType.ALL);
            bleHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("stopLeScan");
                    if( BleManage.getInstance().getMyBleScannerDriver()!=null) {
                        BleManage.getInstance().getMyBleScannerDriver().stop();
                    }
                }
            }, 20000);
        } else {
            BleManage.getInstance().getMyBleScannerDriver().stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BleManage.getInstance().getMySingleDriver().getReconnect().size()!=0) {
            LogUtils.e("onResume:scanLeDevice(true)");
            scanLeDevice(true);
            FileWriteUtils.initWrite("界面可见开启的扫描：onResume（）199"+toString());
        }
    }

    @Override
    public void scanState(BleScannerState state) {

    }

    @Override
    public void scan(BlueDevice device) {
        if(BleManage.getInstance().getMySingleDriver().isReconnectList(device.getDevice().getAddress())){
            LogUtils.e("扫描到重连设备开始重连");
           MyApp.getDriver().removeReconnect(device.getDevice().getAddress());
           MyApp.getDriver().connect(device.getDevice().getAddress(),device.getDevice(),true);
        }
    }
    @Override
    public void deviceStateChange(String mac, DeviceState state) {
        LogUtils.e("设备状态"+ DeviceState.getDeviceState(this,state)+this.toString());
        if(state== DeviceState.CONNECT||state== DeviceState.CONNECTING){
           MyApp.getDriver().getReconnect().clear();
        }
        if(state== DeviceState.CONNECT){
            DeviceBean deviceBean = SharePreferenceDevice.readShareDevice(BaseReconnectBlueActivity.this);
            if((deviceBean==null||deviceBean.getmMac()==null||(!deviceBean.getmMac().equals(mac)))&&MyApp.getDriver().getConnectDevice()!=null){
                LogUtils.e("没有绑定过设备不需要重连");
                deviceBean = new DeviceBean();
                deviceBean.setmMac(mac);
                deviceBean.setmName(MyApp.getDriver().getConnectDevice().getName());
                deviceBean.setBandle(false);
                SharePreferenceDevice.saveShareDevice(BaseReconnectBlueActivity.this,deviceBean);
            }
        }
        FileWriteUtils.initWrite("设备连接状态改变开启的扫描：deviceStateChange（）233"+toString());
        if(BleManage.getInstance().getMySingleDriver().getReconnect().size()==0){
            scanLeDevice(false);
        }else {
            scanLeDevice(true);
        }
    }

    @Override
    public void reConnected(String mac) {
        LogUtils.e("设备重连开始"+MyApp.getDriver().getReconnect().size()+this.toString());
        if(MyApp.getDriver().getReconnect().size()!=0) {
            LogUtils.e("reConnected:scanLeDevice(false)");
            scanLeDevice(true);
            FileWriteUtils.initWrite("设备重连列表进入开启的扫描：reConnected（）247"+mac+toString());
        }
    }

    @Override
    public void onNotifySuccess(String mac) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy:scanLeDevice(false)");
        scanLeDevice(false);
        FileWriteUtils.initWrite("界面销毁关闭扫描：onDestroy（）260"+toString());
        MyApp.getDriver().removeBleStateListener(this);
        BleManage.getInstance().getMyBleScannerDriver().removeBleScannerLister(this);
       MyApp.getDriver().getReconnect().clear();
       MyApp.getDriver().disConnect(false);
        bleHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(myReceiver);
        blueTimer.cancel();
    }
    Handler bleHandler = new Handler();
}
