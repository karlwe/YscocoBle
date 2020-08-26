package com.ys.module.utils;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.ys.module.R;

/**
 *获取验证码按钮的常见功能
 *
 *
 */
public class MyCountTimer extends CountDownTimer {
	public static int TIME_COUNT = 60000;
	private TextView btn;
	private int endStrRid;
	private int normalColor, timingColor;
	Handler handler ;


	public MyCountTimer(long millisInFuture, long countDownInterval, Button btn, int endStrRid) {
		super(millisInFuture, countDownInterval);
		this.btn = btn;
		this.endStrRid = endStrRid;
	}
	public MyCountTimer(TextView btn, int endStrRid,int time_count) {
		super(time_count, 1000);
		this.btn = btn;
		this.endStrRid = endStrRid;
	}

	public MyCountTimer(TextView btn, int endStrRid,Handler handler) {
		super(TIME_COUNT, 1000);
		this.btn = btn;
		this.endStrRid = endStrRid;
		this.handler = handler;
	}

	public MyCountTimer(TextView btn) {
		super(TIME_COUNT, 1000);
		this.btn = btn;
		this.endStrRid = R.string.get_code_text;
	}

	public MyCountTimer(Button tv_varify, int normalColor, int timingColor) {
		this(tv_varify);
		this.normalColor = normalColor;
		this.timingColor = timingColor;
	}

	@Override
	public void onFinish() {
		if (normalColor > 0) {
			btn.setTextColor(normalColor);
		}
		btn.setText(endStrRid);
		btn.setEnabled(true);
		if(handler!=null){
			handler.sendEmptyMessage(1);
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (timingColor > 0) {
			btn.setTextColor(timingColor);
		}
		btn.setEnabled(false);
		btn.setText(millisUntilFinished / 1000 + "s");
	}
}