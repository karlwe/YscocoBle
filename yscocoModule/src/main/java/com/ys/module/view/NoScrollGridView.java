package com.ys.module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 作者：karl.wei
 * 创建日期： 2017/8/17 0017 10:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍: GridView不存在滚动条 ,可以嵌套在ScollView里面完全展示
 */
public class NoScrollGridView extends GridView {
	public NoScrollGridView(Context context) {
		super(context);
	}
	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
