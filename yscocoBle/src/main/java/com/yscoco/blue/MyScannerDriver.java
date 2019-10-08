package com.yscoco.blue;

import com.yscoco.blue.base.BaseScannerDriver;
import com.yscoco.blue.bean.BlueDevice;
import com.yscoco.blue.enums.BleScannerState;
import com.yscoco.blue.listener.BleScannerListener;

import java.util.HashSet;
import java.util.Set;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/10 0010
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：扫描实现类
 */
public class MyScannerDriver extends BaseScannerDriver{
    Set<BleScannerListener> bleScanerSet = new HashSet<BleScannerListener>();
    private MyScannerDriver(BleManage bleManage) {
        super(bleManage);
    }
    private static MyScannerDriver myDriver ;
    public static  MyScannerDriver getInstance(BleManage bleManage){
        if(myDriver==null){
            synchronized (MyScannerDriver.class) {
                if(myDriver==null) {
                    myDriver = new MyScannerDriver(bleManage);
                }
            }
        }
        return myDriver;
    }
    @Override
    public void handlerMsg(BlueDevice device) {
        for (BleScannerListener listner : bleScanerSet) {
            listner.scan(device);
        }
    }

    @Override
    public void handlerMsg(BleScannerState state) {
        for (BleScannerListener listner : bleScanerSet) {
            listner.scanState(state);
        }
    }

    @Override
    public void addBleScannerLister(BleScannerListener listener) {
        bleScanerSet.add(listener);
    }

    @Override
    public void removeBleScannerLister(BleScannerListener listener) {
        bleScanerSet.remove(listener);
    }
}
