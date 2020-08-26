package com.ys.module.select;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.ys.module.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018\3\2 0002.
 */

public class UnLimitDatePickerDetails {

    private static TimePickerView pickerTime;
    private Activity activity;
    private OnPickerTimeClickListener listener;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar selectedDate;
    public UnLimitDatePickerDetails(Activity activity, OnPickerTimeClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        setDate();
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
                .setDividerColor(activity.getResources().getColor(R.color.input_hint_color))
                //滚轮背景颜色 Night mode
                .setBgColor(activity.getResources().getColor(R.color.page_bg_color))
                //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(1.8f)
                //设置选中项的颜色
                .setTextColorCenter(activity.getResources().getColor(R.color.black_two_color))
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pickerTime.returnData();
                                pickerTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                //分隔线的颜色
                .setDividerColor(Color.BLACK)
                .build();
        return pickerTime;
    }

    public void show(){
        pickerTime.show();
    }

    public void dismiss() {
        pickerTime.dismiss();
    }

    private void setDate() {
        /**当前时间*/
        selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(year, month, day);
//        endDate = Calendar.getInstance();
//        endDate.set(year , month, day);
    }



}
