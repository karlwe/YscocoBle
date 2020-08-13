package com.yscoco.yscocoble.base.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ys.module.dialog.LoadingDialog;
import com.ys.module.log.LogUtils;
import com.ys.module.toast.ToastTool;
import com.ys.module.utils.ActivityCollectorUtils;
import com.ys.module.utils.StatusBarUtil;
import com.ys.module.utils.StringUtils;
import com.yscoco.blue.BleManage;
import com.yscoco.blue.enums.DeviceState;
import com.yscoco.yscocoble.R;
import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 作者：karl.wei
 * 创建日期： 2017/8/9 0009 14:11
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：界面基类
 */
public abstract class BaseActivity extends FragmentActivity  {
    private long mExitTime;
    public LocalBroadcastManager lbm;
    public View view;
    private LinearLayout layout;
    public LoadingDialog dialog;

    public void setDisableStatusBar(boolean disableStatusBar) {
        this.disableStatusBar = disableStatusBar;
    }

    //控制是否消除顶部状态栏区域
    private boolean disableStatusBar = false;

    public boolean isDisableStatusBar() {
        return disableStatusBar;
    }


    @Override
    protected void onCreate(Bundle arg0) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        //无title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
//                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        super.onCreate(arg0);
        initStateBar();
        if (!isDisableStatusBar())
            setStateBar();
        ActivityCollectorUtils.addActivity(this);
        ButterKnife.bind(this);
        lbm = LocalBroadcastManager.getInstance(this);
        LogUtils.e("activity::" + toString());
        dialog = new LoadingDialog(this);
        init();
    }
    public void setStateBar() {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        view.setLayoutParams(layoutParams);
    }

    private void initStateBar() {
        layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout, null, false);
        view = new View(this);
        ViewGroup.LayoutParams viewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParams.height = 0;//状态栏默认高度0
        view.setLayoutParams(viewParams);
        view.setBackgroundResource(initColor());
        layout.addView(view);
        View inflate = LayoutInflater.from(this).inflate(setLayoutId(), layout, false);
        ViewGroup.LayoutParams viewParamInf = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(viewParamInf);
        layout.addView(inflate);
        setContentView(layout);
    }

    public int initAlph() {
        return 0;
    }

    public int initColor() {
        return R.color.colorPrimary;
    }

    public void setbg(int color) {
        view.setBackgroundResource(color);
    }

    /**
     * 设置布局的ID
     */
    protected abstract int setLayoutId();

    protected abstract void init();

    public void showActivity(Class<?> cls) {
        showActivity(cls, null);
    }

    public void showActivity(Class<?> cls, int in) {
        Intent i = new Intent(this, cls);
        i.putExtra("value", in);
        startActivity(i);
    }

    public void showActivity(Class<?> cls, String str) {
        Intent i = new Intent(this, cls);
        if (str != null) {
            i.putExtra("value", str);
        }
        startActivity(i);
    }

    /**
     * 是否退出
     *
     * @param keyCode
     * @return
     */
    protected boolean isExit(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastTool.showNormalShort(this, R.string.exit_tips);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return false;
    }

    public void showActivity(Class<?> cls, Serializable obj) {
        Intent i = new Intent(this, cls);
        if (obj != null) {
            i.putExtra("value", obj);
        }
        startActivity(i);
    }

    public void showActivityForResult(Class<?> cls, int requestCode, String str) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("value", str);
        startActivityForResult(intent, requestCode);
    }

    public void showActivityForResult(Class<?> cls, int requestCode, int str) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("value", str);
        startActivityForResult(intent, requestCode);
    }

    public void showActivityForResult(Class<?> cls, int requestCode, Serializable str) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("value", str);
        startActivityForResult(intent, requestCode);
    }

    public void showActivityForResult(Class<?> cls, int requestCode, Parcelable str) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("value", str);
        startActivityForResult(intent, requestCode);
    }

    public Serializable getValue() {
        return getIntent().getSerializableExtra("value");
    }

    public void showActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public void showActivitySetResult(int resultCode, String str) {
        Intent intent = new Intent();
        intent.putExtra(str + "", str);
        setResult(resultCode, intent);
        this.finish();
    }

    public void showActivitySetResult(Class<?> cls, int requstCode, Serializable obj) {
        Intent i = new Intent(this, cls);
        if (obj != null) {
            i.putExtra("value", obj);
        }
        startActivityForResult(i, requstCode);
    }

    public BaseActivity() {
        super();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public boolean isConnect() {
        DeviceState state = BleManage.getInstance().getMySingleDriver().getDeviceState();
        if (state == DeviceState.DISCONNECT || state == DeviceState.DISCONNECTING || state == DeviceState.UNKNOW || state == DeviceState.CONNECTING) {
            ToastTool.showNormalShort(BaseActivity.this, getString(R.string.device_unconnect_text));
            return false;
        }
//        if (state == DeviceState.CONNECTING) {
//            ToastTool.showNormalShort(BaseActivity.this, getString(R.string.wait_connect_finish_text));
//            return false;
//        }
        return true;
    }

    public boolean isConnectForNull() {
        DeviceState state = BleManage.getInstance().getMySingleDriver().getDeviceState();
        if (state == DeviceState.DISCONNECT || state == DeviceState.DISCONNECTING || state == DeviceState.UNKNOW || state == DeviceState.CONNECTING) {
            return false;
        }
        return true;
    }

    /**
     * 请求授权
     */
    public void requestPermission(String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this,
                    permission) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
                ActivityCompat.requestPermissions(this, permissions, 1);
                return;
            }
        }
        okPermissions();
    }

    /**
     * 权限申请返回结果
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意权限申请
                    if (!this.isFinishing()) {
                        okPermissions();
                    }
                } else { //拒绝权限申请
                    notPermissions();
                }
                break;
            default:
                break;
        }
    }

    public void okPermissions() {
    }

    public void notPermissions() {
    }
}
