package com.ys.module.select;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.ys.module.R;
import com.ys.module.log.LogUtils;
import com.ys.module.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017\7\11 0011.
 */

public class UnLimitDatePicker {
    private static TimePickerView pickerTime;
    private Activity activity;
    private OnPickerTimeClickListener listener;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar selectedDate;

    public UnLimitDatePicker(Activity activity, String str, OnPickerTimeClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        if (!StringUtils.isEmpty(str) || str.length() > 10) {
            int one = Integer.parseInt(str.substring(0, 4));
            int two = Integer.parseInt(str.substring(5, 7));
            int three = Integer.parseInt(str.substring(8, 10));
            setDate(one, two, three);
            LogUtils.e("1：" + str);
            LogUtils.e("2：" + one);
            LogUtils.e("3：" + two);
            LogUtils.e("4：" + three);
        } else {
            setDate();
            LogUtils.e("2");
        }
        getInstance();
    }

    public TimePickerView getInstance() {
        pickerTime = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (listener != null) {
                    listener.onSelect(date, v);
                }
            }
        })  /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
                //设置分割线的颜色
               .setDividerColor(Color.WHITE)
               //设置选中项的颜色
                .setTextColorCenter(Color.LTGRAY)
                //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(1.6f)
                //标题背景颜色 Night mode
                .setTitleBgColor(Color.DKGRAY)
                //滚轮背景颜色 Night mode
                .setBgColor(Color.BLACK)
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)
                // default is center
               .gravity(Gravity.RIGHT)
               */
                .setDate(selectedDate)
                //设置分割线的颜色
                .setDividerColor(activity.getResources().getColor(R.color.black_two_color))
                //滚轮背景颜色 Night mode
                .setBgColor(activity.getResources().getColor(R.color.white_bg_color))
                //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(1.8f)
                //设置选中项的颜色
                .setTextColorCenter(activity.getResources().getColor(R.color.black_two_color))
                .setRangDate(startDate, endDate)
                .setCancelColor(activity.getResources().getColor(R.color.black_two_color))
                .setLayoutRes(R.layout.pickerview_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.btnSubmit);
                        final TextView tvCancel = (TextView) v.findViewById(R.id.btnCancel);
                        final RelativeLayout rv_topbar = (RelativeLayout) v.findViewById(R.id.rv_topbar);
                        rv_topbar.setBackgroundColor(activity.getResources().getColor(R.color.white_bg_color));
                        tvCancel.setTextColor(activity.getResources().getColor(R.color.black_two_color));
                        tvSubmit.setTextColor(activity.getResources().getColor(R.color.black_two_color));
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pickerTime.returnData();
                                pickerTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                //分隔线的颜色
                .setDividerColor(Color.rgb(0x45,0x45,0x45))
                .build();
        return pickerTime;
    }

    public void show() {
        pickerTime.show();
    }

    public void dismiss() {
        pickerTime.dismiss();
    }

    public void setDate(int year, int month, int day) {
        /**当前时间*/
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        selectedDate = Calendar.getInstance();

        int yea = selectedDate.get(Calendar.YEAR);
        int mont = selectedDate.get(Calendar.MONTH);
        int da = selectedDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(1970, 12, 1);
        endDate.set(yea, mont, da);
        selectedDate.set(year, month - 1, day);
    }

    public void setDate() {
        /**当前时间*/
        selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(1959, 10, 1);
        endDate = Calendar.getInstance();
        endDate.set(year, month, day);
    }
}
