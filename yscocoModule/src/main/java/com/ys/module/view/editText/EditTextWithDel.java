package com.ys.module.view.editText;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.ys.module.R;



/**
 * 作者：karl.wei
 * 创建日期： 2017/8/17 0017 10:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：带删除按钮的编辑框
 */
public class EditTextWithDel extends AppCompatEditText {
	private final static String TAG = "EditTextWithDel";
	private Drawable imgInable;
	private Drawable imgAble;
	private Drawable imSearch;
	private Context mContext;

	public EditTextWithDel(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		int fivty = (int)mContext.getResources().getDimension(R.dimen.DIMEN_45PX);
		imgInable = DrawbleUtils.zoomDrawable(mContext.getResources().getDrawable(R.drawable.delete),fivty*2,fivty*2);
		imgAble = DrawbleUtils.zoomDrawable(mContext.getResources().getDrawable(R.drawable.delete),fivty*2,fivty*2);
		imSearch = DrawbleUtils.zoomDrawable(mContext.getResources().getDrawable(R.drawable.search),fivty*2,fivty*2);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}
	private void setDrawable() {
		if (length() < 1)
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
//		setCompoundDrawablesWithIntrinsicBounds(imSearch, null, null, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 20;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
