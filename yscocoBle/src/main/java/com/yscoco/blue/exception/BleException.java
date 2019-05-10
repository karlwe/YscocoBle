package com.yscoco.blue.exception;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/8 0008
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：蓝牙连接异常
 */
public class BleException extends RuntimeException {
    public BleException() {
        super();
    }
    public BleException(String message) {
        super(message);
    }

    public BleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BleException(Throwable cause) {
        super(cause);
    }
}
