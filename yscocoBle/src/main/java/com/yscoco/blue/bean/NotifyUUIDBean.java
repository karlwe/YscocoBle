package com.yscoco.blue.bean;

import java.io.Serializable;

/**
 * 作者：karl.wei
 * 创建日期： 2018/11/30 0030
 * 邮箱：karl.wei@yscoco.com
 * QQ：2736764338
 * 类介绍：notify模式的ServiceUUID,CHARUUID
 */
public class NotifyUUIDBean implements Serializable{
    private String serviceUUID;
    private String charUUID;

    public NotifyUUIDBean(String serviceUUID, String charUUID) {
        this.serviceUUID = serviceUUID;
        this.charUUID = charUUID;
    }

    public String getServiceUUID() {
        return serviceUUID;
    }

    public void setServiceUUID(String serviceUUID) {
        this.serviceUUID = serviceUUID;
    }

    public String getCharUUID() {
        return charUUID;
    }

    public void setCharUUID(String charUUID) {
        this.charUUID = charUUID;
    }
}
