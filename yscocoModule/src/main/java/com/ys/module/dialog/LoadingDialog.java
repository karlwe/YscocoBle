package com.ys.module.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.ys.module.R;
import com.ys.module.utils.StringUtils;
/**
 * 作者：karl.wei
 * 创建日期： 2017/8/9 0009 14:11
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：加载进度条
 */
public class LoadingDialog extends Dialog {

	private TextView mProgressText;
	Context context;

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init();
	}

	public LoadingDialog(Context context) {
		super(context, R.style.MyAlertDialog);
		this.context = context;
		init();
	}

	private void init() {
		setContentView(R.layout.dialog_loadings);
		mProgressText = (TextView) findViewById(R.id.progress_value);
//		setCancelable(false);
	}

	public void show(String text) {
		if (StringUtils.isEmpty(text)) {
//			text = getContext().getString(R.string.loading);
		}
		mProgressText.setText(text);
		if (context!=null&&!((Activity)context).isFinishing() && !this.isShowing()) {
			show();
		}
	}
	public void cancel(){
		if (context!=null&&!((Activity)context).isFinishing() && this.isShowing()) {
			dismiss();
		}
	}
}
