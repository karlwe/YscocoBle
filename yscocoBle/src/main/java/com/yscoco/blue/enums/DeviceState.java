package com.yscoco.blue.enums;

import android.content.Context;

import com.yscoco.blue.R;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/8 0008
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：设备的状态
 */
public enum DeviceState {
    /*连接中*/
    CONNECTING,
    /*连接成功*/
    CONNECT,
    /*断开连接*/
    DISCONNECT,
    /*断开连接中*/
    DISCONNECTING,
    /*不存在*/
    UNKNOW;
    public static String getDeviceState( Context mContext,DeviceState state){
        switch (state){
            case CONNECTING:
               return mContext.getString(R.string.connectting_text);
            case CONNECT:
                return mContext.getString(R.string.connected_text);
            case DISCONNECT:
                return mContext.getString(R.string.disconnect_text);
            case DISCONNECTING:
                return mContext.getString(R.string.disconnect_text);
            default:
                return mContext.getString(R.string.disconnect_text);
        }
    };
}
