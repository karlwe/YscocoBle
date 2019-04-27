package com.yscoco.blue.base;

import android.os.Message;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/12 0012
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：
 */
public interface HandleDriver {
    public abstract void dataHandler(String uuid,String mac, byte[] data,int what);
    public abstract void handlerMsg(String mac,Message msg);
    public void sendMessage(String mac,int what, Object data) ;
    public void sendMessage(String mac, int what);
}
