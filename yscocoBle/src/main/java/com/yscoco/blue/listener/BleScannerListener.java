package com.yscoco.blue.listener;

import com.yscoco.blue.bean.BlueDevice;
import com.yscoco.blue.enums.BleScannerState;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/10 0010
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙扫描广播
 */
public interface BleScannerListener {
    public void scanState(BleScannerState state);
    public void scan(BlueDevice device);
}
