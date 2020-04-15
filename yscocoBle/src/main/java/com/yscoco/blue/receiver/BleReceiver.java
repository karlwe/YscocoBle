package com.yscoco.blue.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yscoco.blue.BleManage;
import com.yscoco.blue.utils.FileWriteUtils;
import com.yscoco.blue.utils.LogBlueUtils;

public class BleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                LogBlueUtils.e("蓝牙开关状态关闭状态开启的扫描：ACTION_STATE_CHANGED::"+state);
                FileWriteUtils.initWrite("蓝牙开关状态关闭状态开启的扫描：ACTION_STATE_CHANGED::"+state);
                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        BleManage.getInstance().getMySingleDriver().disConnect(true);
                        BleManage.getInstance().getMyMoreDriver().disConnectAll(null);
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                }
                break;
        }
    }
}
