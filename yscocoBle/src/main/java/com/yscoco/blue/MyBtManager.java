package com.yscoco.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.yscoco.blue.base.BaseBtManager;
import com.yscoco.blue.base.HandleDriver;
import com.yscoco.blue.enums.DeviceState;
import com.yscoco.blue.exception.BleException;
import com.yscoco.blue.utils.FileWriteUtils;
import com.yscoco.blue.utils.LogBlueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * 作者：karl.wei
 * 创建日期： 2017/12/9 0009 13:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：蓝牙设备传输类
 */
public class MyBtManager extends BaseBtManager {
    private BluetoothManager mBluetoothManager;// 蓝牙管理器
    private BluetoothAdapter mBluetoothAdapter;// 蓝牙适配器
    TimerTask task;
    Timer rssiTimer;
    public int sendFail = 0;
    /**
     * mac地址
     */
    private String mMac;
    public String getmMac() {
        return mMac;
    }
    /**
     * 连接状态
     */
    private DeviceState deviceState = DeviceState.DISCONNECT;
    /**
     * 是否重连
     */
    private boolean isReconnect = true;
    /**
     * 是否断开过,断开过不再发送断开连接提醒
     */
    private boolean isDisconnected = false;
    private BluetoothGatt mBluetoothGatt;
    public BluetoothGatt getmBluetoothGatt() {
        return mBluetoothGatt;
    }
    public void setmBluetoothGatt(BluetoothGatt mBluetoothGatt) {
        this.mBluetoothGatt = mBluetoothGatt;
    }

    protected Context mContext;
    private BluetoothDevice mDevice;
    private HandleDriver mBlueDriver;
    private Handler mHandler = new Handler();
    Runnable disConnectRun = new Runnable() {
        @Override
        public void run() {
            LogBlueUtils.e("超时链接断开重连");
            FileWriteUtils.initWrite("超时链接断开重连");
            disConnect(getmMac(),isReconnect());
        }
    };
    public MyBtManager(Context c, String mac, BluetoothDevice mDevice, HandleDriver blueDriver) {
        mContext = c;
        mMac = mac;
        mBlueDriver = blueDriver;
        this.mDevice = mDevice;
        initialize();
    }
    public BluetoothDevice getmDevice() {
        return mDevice;
    }

    public void setmDevice(BluetoothDevice mDevice) {
        this.mDevice = mDevice;
    }
    public boolean connected(){
        if(deviceState==DeviceState.CONNECT||deviceState==DeviceState.CONNECTING){
            LogBlueUtils.e("链接中"+deviceState);
            FileWriteUtils.initWrite("98链接中"+deviceState);
            return true;
        }
        //初始化
        if (!initialize()) {
            LogBlueUtils.e("初始化失败");
            FileWriteUtils.initWrite("104初始化失败");
            return false;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            LogBlueUtils.e("蓝牙未打开");
            FileWriteUtils.initWrite("1110蓝牙未打开");
            return false;
        }

        // 有为空的情况就直接返回
        if (mBluetoothAdapter == null || mMac == null) {
            LogBlueUtils.e("适配器为空："+mBluetoothAdapter);
            FileWriteUtils.initWrite("117适配器为空："+mBluetoothAdapter);
            return false;
        }
//        if(!isConnected) {
        close();
//        }
        //设为断开可以重连
        isReconnect = true;
        // 断开连接
        if (deviceState==DeviceState.CONNECT) {
            if (mBluetoothGatt != null) {
                mBluetoothGatt.disconnect();
                mBluetoothGatt = null;
            }
        }
//         以前连接设备。尝试重新连接。
//         Previously onConnected device. Try to reconnect.
//        if (mMac != null && mBluetoothGatt != null) {
//           LogBlueUtils.d( "Trying to use an existing mBluetoothGatt for connection.");
//            mBluetoothGatt.connect();
//        }
        final BluetoothDevice device;
        try {
            device = mBluetoothAdapter.getRemoteDevice(mMac.toUpperCase().trim());
            if(device==null){
                LogBlueUtils.e("设备未初始化成功，断开连接");
                FileWriteUtils.initWrite("设备未初始化成功，断开连接");
                mHandler.post(disConnectRun);
                return false;
            }
            if(mDevice==null){
                mDevice = device;
            }
            mHandler.postDelayed(disConnectRun,15000);
            deviceState = DeviceState.CONNECTING;
            mBlueDriver.sendMessage(mMac,CONNECTING);/*连接开始*/
            mBluetoothGatt = mDevice.connectGatt(mContext, false, mGattCallback);
//            mBluetoothGatt.connect();e
            LogBlueUtils.e( mMac+"device.getBondState==" + mDevice.getBondState());
            FileWriteUtils.initWrite(mMac+"device.getBondState==" + mDevice.getBondState());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogBlueUtils.e( "设备出现异常==" +e.toString()  );
            FileWriteUtils.initWrite("161设备出现异常==" +e.toString()  );
        }
        return true;
    }
    public DeviceState getDeviceState() {
        return deviceState;
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    public void disConnect(String mac, boolean isReconnect) {
        LogBlueUtils.e("disconnect："+isReconnect);
        this.isReconnect = isReconnect;
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        if(mac.equals(mMac)){
            LogBlueUtils.e("deviceState:"+deviceState);
            if(deviceState==DeviceState.CONNECT||deviceState==DeviceState.CONNECTING){
                mBlueDriver.sendMessage(mMac,DISCONNECT);/*断开连接开始*/
                deviceState = DeviceState.DISCONNECT;
                LogBlueUtils.e("deviceState222:"+deviceState);
            }
            if(isReconnect()){
                mBlueDriver.sendMessage(mMac,RE_CONNECT);
            }
            if (mBluetoothGatt != null) {
                mBluetoothGatt.disconnect();
            }
        }
    }
    /*设备是否处于重连状态*/
    public void setReconnect(boolean isReconnect) {
        this.isReconnect = isReconnect;
    }

    public boolean isReconnect() {
        return isReconnect;
    }

    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter
        // through
        // BluetoothManager.
//        if (mBluetoothManager == null) {
            if (mBluetoothManager == null) {
                mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
               if(mBluetoothManager==null){
                   return false;
               }
            }
//        }
        if(mBluetoothAdapter==null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                return false;
            }
        }
        return true;
    }

    // Implements callback methods for GATT events that the app cares about. For
    // example,
    // connection change and services discovered.
    // 通过BLE API的不同类型的回调方法
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            LogBlueUtils.d("连接回调：status" + status + "，newState" + newState + "," + gatt.getDevice().getAddress());
            FileWriteUtils.initWrite("连接回调：status" + status + "，newState" + newState + "," + gatt.getDevice().getAddress());
            mHandler.removeCallbacks(disConnectRun);
            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                FileWriteUtils.initWrite("设备连接"+this.toString());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //已连接
                        mBluetoothGatt.discoverServices();
                    }
                },500);
            } else {//已断开
                FileWriteUtils.initWrite("设备断开连接"+this.toString()+"是否重连"+isReconnect());
                mHandler.removeCallbacksAndMessages(null);
                deviceState = DeviceState.DISCONNECT;
                mBluetoothGatt.close();
                mBluetoothGatt = null;
                if(!isDisconnected){
                    mBlueDriver.sendMessage(mMac,DISCONNECT);
                }
                isDisconnected = true;
                if (isReconnect()) {
                    mBlueDriver.sendMessage(mMac,RE_CONNECT);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                deviceState = DeviceState.CONNECT;
                isDisconnected = false;
                mBlueDriver.sendMessage(mMac,CONNECTED);
                LogBlueUtils.d("发现服务成功");
                FileWriteUtils.initWrite("发现服务成功");
//                mBlueDriver.sendMessage(mMac, MyMoreBleDriver.MSG, "设备连接成功");
                List<BluetoothGattService> services =  gatt.getServices();
                for(BluetoothGattService service:services){
                    LogBlueUtils.d("serviceUUID:"+service.getUuid().toString());
                    FileWriteUtils.initWrite("serviceUUID:"+service.getUuid().toString());
                    if(service.getCharacteristics()!=null){
                        for(BluetoothGattCharacteristic cha:service.getCharacteristics()){
                            LogBlueUtils.d("characteristicUUID:"+cha.getUuid().toString());
                            FileWriteUtils.initWrite("characteristicUUID:"+cha.getUuid().toString());
                            if(cha.getDescriptors()!=null){
                                for(BluetoothGattDescriptor des:cha.getDescriptors()) {
                                    LogBlueUtils.d("BluetoothGattDescriptorUUID:" + des.getUuid().toString());
                                    FileWriteUtils.initWrite("BluetoothGattDescriptorUUID:" + des.getUuid().toString());
                                }
                            }
                        }
                    }
                }
                startNotification(BleManage.getInstance().getBleConfig().getNotifyList().get(0).getServiceUUID()
                        ,BleManage.getInstance().getBleConfig().getNotifyList().get(0).getCharUUID());
            }else{
                LogBlueUtils.d("发现服务失败");
                FileWriteUtils.initWrite("发现服务失败");
                disConnect(mMac,true);
            }
            super.onServicesDiscovered(gatt, status);
        }

        /**
         * 返回数据。
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//           LogBlueUtils.d("onCharacteristicChanged:" + "有数据");
            // 获取特征值的UUID
            String uuid = characteristic.getUuid().toString().trim();
            final byte[] data = characteristic.getValue();
            StringBuffer b = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                b.append(String.format("%02X ", data[i]).toString().trim());
            }
            LogBlueUtils.d("Notify："+mMac+"数据为" + b.toString());
            FileWriteUtils.initWrite("Notify："+mMac+"数据为" + b.toString());
            mBlueDriver.dataHandler(uuid,mMac,data,NOTIFY_VALUE);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
//                mBlueDriver.sendMessage(mMac, MyMoreBleDriver.MSG, "读取电量失败");
                // 获取特征值的UUID
                String uuid = characteristic.getUuid().toString().trim();
                final byte[] data = characteristic.getValue();
                StringBuffer b = new StringBuffer();
                for (int i = 0; i < data.length; i++) {
                    b.append(String.format("%02X ", data[i]).toString().trim());
                }
                LogBlueUtils.d("onCharacteristicRead："+mMac+"数据为" + b.toString());
                FileWriteUtils.initWrite("onCharacteristicRead："+mMac+"数据为" + b.toString());
                mBlueDriver.dataHandler(uuid,mMac,data,READ_VALUE);
            }else{
                disConnect(mMac,true);
                LogBlueUtils.d("onCharacteristicRead:失败,characteristic.getService()UUID:"+characteristic.getService().getUuid().toString()+",BluetoothGattCharacteristic UUID:"+characteristic.getUuid().toString());
                FileWriteUtils.initWrite("onCharacteristicRead:失败,characteristic.getService()UUID:"+characteristic.getService().getUuid().toString()+",BluetoothGattCharacteristic UUID:"+characteristic.getUuid().toString());
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            LogBlueUtils.d("onCharacteristicWrite" + status);
            FileWriteUtils.initWrite("onCharacteristicWrite" + status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            if (status != BluetoothGatt.GATT_SUCCESS) {
                disConnect(mMac,true);
                LogBlueUtils.w("开启 BluetoothGattCharacteristic UUID为"+descriptor.getCharacteristic().getUuid().toString().toUpperCase()+"notify异常:"+status);
                FileWriteUtils.initWrite("开启 BluetoothGattCharacteristic UUID为"+descriptor.getCharacteristic().getUuid().toString().toUpperCase()+"notify异常:"+status);
                return;
            }
            LogBlueUtils.d("开启notify成功"+descriptor.getCharacteristic().getUuid().toString().toUpperCase());
            FileWriteUtils.initWrite("开启notify成功"+descriptor.getCharacteristic().getUuid().toString().toUpperCase());
            for(int i=0;i<BleManage.getInstance().getBleConfig().getNotifyList().size();i++){
                if(descriptor.getCharacteristic().getUuid().toString().toUpperCase()
                        .equals(BleManage.getInstance().getBleConfig().getNotifyList().get(i).getCharUUID().toUpperCase())) {
                    if(i==(BleManage.getInstance().getBleConfig().getNotifyList().size()-1)){
                        /*数据通道开启成功*/
                        mBlueDriver.sendMessage(mMac,NOTIFY_ON);
                        LogBlueUtils.d("Notify全部开启，可以开始同步数据");
                        FileWriteUtils.initWrite("Notify全部开启，可以开始同步数据");
                    }else{
                        startNotification(BleManage.getInstance().getBleConfig().getNotifyList().get(i+1).getServiceUUID(),
                                BleManage.getInstance().getBleConfig().getNotifyList().get(i+1).getCharUUID());
                    }
                }
            }
        }
        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Message msg = new Message();
            mBlueDriver.sendMessage(mMac,NOTIFY_RSSI,rssi);

        }
    };
    private ArrayList<Integer> mRssis = new ArrayList<>();
    /**
     * @param service_uuid 服务的UUID
     * @param cha_uuid     特征值的UUID
     * @param bb           发送的字节数组的值
     */
    @SuppressWarnings("unused")
    public synchronized boolean writeLlsAlertLevel(String service_uuid, String cha_uuid, byte[] bb,int type) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < bb.length; i++) {
            b.append(String.format("%02X ", bb[i]).toString().trim());
        }
        LogBlueUtils.d("发送:"+cha_uuid+":" + b.toString() +"设备mac:"+ mMac);
        FileWriteUtils.initWrite("发送:" + b.toString() +"设备mac:"+ mMac);
        BluetoothGattService linkLossService;
        BluetoothGattCharacteristic alertLevel = getCharacter(service_uuid, cha_uuid);
        if (alertLevel == null) {
            LogBlueUtils.d("Service-UUID为"+service_uuid+"Characteristic-UUID为"+cha_uuid+"不存在!");
            return false;
        }
        boolean status = false;
        int storedLevel = alertLevel.getWriteType();
        alertLevel.setValue(bb);
        alertLevel.setWriteType(type);
        if(mBluetoothGatt!=null) {
            status = mBluetoothGatt.writeCharacteristic(alertLevel);
        }
        if(status){
            sendFail =0;
        }else{
            ++sendFail;
            if(sendFail>4){
                sendFail =0;
                disConnect(mMac,isReconnect());
            }
        }
        LogBlueUtils.e("数据写入状态" + status);
        FileWriteUtils.initWrite("数据写入状态" + status);
        return status;
    }

    /**
     * 获取特征值
     */
    public BluetoothGattCharacteristic getCharacter(String serviceUUID, String characterUUID) {
        // Log.e("error","设备名称："+mBluetoothGatt.getDevice().getAddress());
        if (mBluetoothGatt == null) {
            return null;
        }
        BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString(serviceUUID));
        if (service != null) {
            BluetoothGattCharacteristic c1 = service.getCharacteristic(UUID.fromString(characterUUID));
            if(c1==null){
                disConnect(mMac,false);
                LogBlueUtils.w("BluetoothGattCharacteristic UUID为"+characterUUID+"的通道不存在");
                FileWriteUtils.initWrite("BluetoothGattCharacteristic UUID为"+characterUUID+"的通道不存在");

            }
            return c1;
        }else{
            disConnect(mMac,false);
            LogBlueUtils.w("BluetoothGattService UUID为"+serviceUUID+"的服务不存在");
            FileWriteUtils.initWrite("BluetoothGattService UUID为"+serviceUUID+"的服务不存在");
            return null;
        }
    }

    /**
     * 开启通知
     */
    private void startNotification(String serviceUUID, String charaterUUID) {
        BluetoothGattCharacteristic c1 = getCharacter(serviceUUID, charaterUUID);
        if (c1 != null) {
            final int cx1 = c1.getProperties();
            if ((cx1 | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // 如果有一个活跃的通知上的特点,明确第一所以不更新用户界面上的数据字段。
//                if (c1 != null) {
//                    setCharacteristicNotification(c1, false);
//                }
//                readCharacteristic(serviceUUID, charaterUUID);
                if ((cx1 | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    setCharacteristicNotification(c1, true);
                }else{
                    disConnect(mMac,false);
                    LogBlueUtils.w("BluetoothGattService UUID为"+serviceUUID+",BluetoothGattCharacteristic UUID为"+charaterUUID+"没有notify权限");
                    FileWriteUtils.initWrite("BluetoothGattService UUID为"+serviceUUID+",BluetoothGattCharacteristic UUID为"+charaterUUID+"没有notify权限");

                }
            }
        }else{
            disConnect(mMac,true);
        }
    }

    /**
     * 读取特征值的Values
     */
    public synchronized void readCharacteristic(String service_uuid, String cha_uuid) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogBlueUtils.w("读取数据的通道不存在");
            FileWriteUtils.initWrite("读取数据的通道不存在");
            return;
        }
        BluetoothGattCharacteristic c1 = getCharacter(service_uuid, cha_uuid);
        if (c1 != null&&mBluetoothGatt!=null) {
            mBluetoothGatt.readCharacteristic(c1);
        }
    }

    /**
     * 设置通知开启
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogBlueUtils.d("BluetoothAdapter not initialized");
            FileWriteUtils.initWrite("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, true);
        // Log.e("taa","开启了广播");
        LogBlueUtils.d("开启广播");
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(DES_UUID1));
        if (descriptor != null&&mBluetoothGatt!=null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }else{
            if(descriptor==null){
                for(int i=0;i<BleManage.getInstance().getBleConfig().getNotifyList().size();i++){
                    if(characteristic.getUuid().toString().toUpperCase()
                            .equals(BleManage.getInstance().getBleConfig().getNotifyList().get(i).getCharUUID().toUpperCase())) {
                        if(i==(BleManage.getInstance().getBleConfig().getNotifyList().size()-1)){
                            /*数据通道开启成功*/
                            mBlueDriver.sendMessage(mMac,NOTIFY_ON);
                            LogBlueUtils.d("Notify全部开启，可以开始同步数据");
                            FileWriteUtils.initWrite("Notify全部开启，可以开始同步数据");
                        }else{
                            startNotification(BleManage.getInstance().getBleConfig().getNotifyList().get(i+1).getServiceUUID(),
                                    BleManage.getInstance().getBleConfig().getNotifyList().get(i+1).getCharUUID());
                        }
                    }
                }
                LogBlueUtils.w("BluetoothGattService UUID为"+characteristic.getService().getUuid().toString()+",BluetoothGattCharacteristic UUID为"+characteristic.getUuid().toString()+",BluetoothGattDescriptor UUID为"+DES_UUID1+"的特征值不存在");
                FileWriteUtils.initWrite("BluetoothGattService UUID为"+characteristic.getService().getUuid().toString()+",BluetoothGattCharacteristic UUID为"+characteristic.getUuid().toString()+",BluetoothGattDescriptor UUID为"+DES_UUID1+"的特征值不存在");
            }
        }
    }

    public synchronized void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public boolean writeData(byte[] cmd){
        return writeLlsAlertLevel(BleManage.getInstance().getBleConfig().SERVICE_UUID1,BleManage.getInstance().getBleConfig().CHA_WRITE,cmd,BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
    }
    public boolean writeData( byte[] cmd,String serviceUUID,String charUUID){
        return writeLlsAlertLevel(serviceUUID,charUUID,cmd,BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
    }

    public boolean writeData(byte[] cmd,int type){
        return writeLlsAlertLevel(BleManage.getInstance().getBleConfig().SERVICE_UUID1,BleManage.getInstance().getBleConfig().CHA_WRITE,cmd,type);
    }
    public boolean writeData( byte[] cmd,String serviceUUID,String charUUID,int type){
        return writeLlsAlertLevel(serviceUUID,charUUID,cmd,type);
    }
    /**
     * 读取数据
     */
    public void readData(){
        readCharacteristic(BleManage.getInstance().getBleConfig().SERVICE_UUID1,BleManage.getInstance().getBleConfig().CHA_NOTIFY);
    };
    /**
     * 读取特定服务和属性的数据
     */
    public void readData(String serviceUUID,String charUUID){
        readCharacteristic(serviceUUID,charUUID);
    };
}
