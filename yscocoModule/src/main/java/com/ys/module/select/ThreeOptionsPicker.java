package com.ys.module.select;

import android.app.Activity;
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

public class ThreeOptionsPicker<T> {
    private Activity activity;
    private OnPickerThreeOptionsClickListener listener;
    private OptionsPickerView pvOptions;
    private List<T> optinos1,optinos2,optinos3;
    private String title;
    private int selectPositionOne,selectPositionTwo,selectPositionThree;
    private boolean isThree;
    /**带title */
    public ThreeOptionsPicker(Activity activity,String title,
                              String selectOne,String selectTwo,String selectThree,
                              List<T> optinos1, List<T> optinos2, List<T> optinos3,boolean isThree,
                              OnPickerThreeOptionsClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.optinos1 = optinos1;
        this.optinos2 = optinos2;
        this.optinos3 = optinos3;
        this.title = title;
        boolean isContinue = true;
        this.isThree = isThree;
        for(int i=0;i<optinos1.size()&&isContinue&&selectOne!=null;i++){
            if(selectOne.equals(optinos1.get(i))){
                selectPositionOne = i;
                isContinue = false;
            }
        }
        isContinue = true;
        for(int i=0;i<optinos2.size()&&isContinue&&selectTwo!=null;i++){
            if(selectTwo.equals(optinos2.get(i))){
                selectPositionTwo = i;
                isContinue = false;
            }
        }
        isContinue = true;
        for(int i=0;i<optinos3.size()&&isContinue&&selectThree!=null;i++){
            if(selectThree.equals(optinos3.get(i))){
                selectPositionThree = i;
                isContinue = false;
            }
        }
        getInstance();
    }


    private OptionsPickerView getInstance() {
        pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView.
                OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if(listener != null) {
                    listener.onSelect(options1,options2,options3,v);
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
                .isCenterLabel(true)
                //分隔线的颜色
                .setDividerColor(activity.getResources().getColor(R.color.black_three_color))
                .setTitleText(title)
                .setCyclic(false,true,true)
                .setSelectOptions(selectPositionOne,selectPositionTwo,selectPositionThree)//默认选中
                .setLayoutRes(R.layout.item_picker_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        ((TextView)v.findViewById(R.id.tvTitle)).setText(StringUtils.nullTanst(title));
                        if(isThree){
                            v.findViewById(R.id.options3).setVisibility(View.VISIBLE);
                        }else{
                            v.findViewById(R.id.options3).setVisibility(View.GONE);
                        }
                        v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });
                    }
                })
                .build();

        pvOptions.setNPicker(optinos1,optinos2,optinos3);

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
