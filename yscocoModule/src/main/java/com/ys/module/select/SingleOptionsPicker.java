package com.ys.module.select;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.ys.module.R;
import com.ys.module.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017\7\11 0011.
 */

public class SingleOptionsPicker<T> {
    private Activity activity;
    private OnPickerOptionsClickListener listener;
    private OptionsPickerView pvOptions;
    OptionsPickerView pvCustomOptions;
    private List<T> itemData;
    private String title;
    private int position;
    /**带title */
    public SingleOptionsPicker(Activity activity,String title,String select, List<T> itemData,
                               OnPickerOptionsClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.itemData = itemData;
        this.title = title;
        boolean isContinue = true;
        for(int i=0;i<itemData.size()&&isContinue;i++){
            if(select.equals(itemData.get(i))){
                position = i;
                isContinue = false;
            }
        }

        getInstance();
//        getPicker();
    }


    private OptionsPickerView getInstance() {
        pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView.
                OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if(listener != null) {
                    listener.onSelect(options1,v);
                }
            }
        })
                .setDividerColor(activity.getResources().getColor(R.color.input_hint_color))
                //滚轮背景颜色 Night mode
                .setBgColor(activity.getResources().getColor(R.color.page_bg_color))
                //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(2.0f)
                //设置选中项的颜色
                .setTextColorCenter(activity.getResources().getColor(R.color.black_two_color))
                .setLinkage(false)//选择是否串联
                .isCenterLabel(true)
                //分隔线的颜色
                .setDividerColor(activity.getResources().getColor(R.color.black_three_color))
                .setTitleText(title)
                .setSelectOptions(position)//默认选中
                .setLayoutRes(R.layout.item_picker_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        ((TextView)v.findViewById(R.id.tvTitle)).setText(StringUtils.nullTanst(title));
                        v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });
                        v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });
                    }
                })
                .build();

        pvOptions.setPicker(itemData);

        return pvOptions;
    }


    public void show() {
        if(pvOptions != null && !pvOptions.isShowing()) {
            pvOptions.show();
        }
    }

    public void dismiss() {
        if(pvOptions != null && pvOptions.isShowing()) {
            pvOptions.dismiss();
        }
    }
}
