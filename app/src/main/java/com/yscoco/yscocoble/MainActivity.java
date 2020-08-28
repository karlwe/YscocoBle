package com.yscoco.yscocoble;

import com.ys.module.log.LogUtils;
import com.yscoco.blue.utils.BleUtils;
import com.yscoco.yscocoble.base.activity.BaseReconnectBlueActivity;

public class MainActivity extends BaseReconnectBlueActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        LogUtils.e(BleUtils.toHexString(new byte[]{0x01,0x01,0x03,0x04},";"));
    }
}
