package com.yscoco.blue.imp;

import com.yscoco.blue.enums.ScanNameType;
import com.yscoco.blue.listener.BleScannerListener;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/10 0010
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：扫描的接口类
 */
public interface ScannerDriver {
    public void scan(String name, ScanNameType type);
    public void stop();
    public void addBleScannerLister(BleScannerListener listener);
    public void removeBleScannerLister(BleScannerListener listener);
    public boolean isScanner();
}
