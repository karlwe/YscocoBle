package com.yscoco.yscocoble;

import android.Manifest;
import android.os.Handler;

import com.ys.module.dialog.ConfigDialogUtils;
import com.ys.module.utils.ActivityCollectorUtils;
import com.yscoco.blue.BleManage;
import com.yscoco.yscocoble.base.activity.BaseActivity;

/**
 * 启动页面
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    public boolean isDisableStatusBar() {
        return true;
    }

    @Override
    protected void init() {
        requestPermission(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.READ_CALL_LOG});
    }

    private void start() {
        if(!BleManage.getInstance().isSupportBle()){
            new ConfigDialogUtils(WelcomeActivity.this).builder().setTitle(R.string.warn_info_text)
                    .setContent(R.string.un_support_ble).setLeft(R.string.cancel_text).setLeftBack(new ConfigDialogUtils.LeftCallBack() {
                @Override
                public void leftBtn(int what) {
                    ActivityCollectorUtils.finishAll();
                    finish();
                }
            }).setRight(R.string.continue_text).setRightBack(new ConfigDialogUtils.RightCallBack() {
                @Override
                public void rightBtn(int what) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showActivity(MainActivity.class);
                            finish();
                        }
                    }, 2000);
                }
            });
            return ;
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        showActivity(MainActivity.class);
                        finish();
                }
            }, 2000);
        }
    }

    @Override
    public void okPermissions() {
        super.okPermissions();
        start();
    }

    @Override
    public void notPermissions() {
        super.notPermissions();
        ActivityCollectorUtils.finishAll();
        finish();
    }
}
