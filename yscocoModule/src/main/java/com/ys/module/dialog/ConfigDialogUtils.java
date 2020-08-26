package com.ys.module.dialog;/**
 * Created by Administrator on 2017/8/9 0009.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.module.R;

/**
 * 作者：karl.wei
 * 创建日期： 2017/8/9 0009 14:11
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：常见的弹出框，可以选择确定取消，提醒
 */
public class ConfigDialogUtils {
    private Context mContext;
    private Display display;
    private Dialog dialog;
    private int what = 0;/*返回的内容*/
    LinearLayout lLayout_content;/*整个父控件*/
    private TextView txt_title;/*标题栏*/
    private TextView txt_content;/*内容*/
    private Button btn_left;/*左侧按钮*/
    private Button btn_right;/*右侧按钮*/
    private View img_line;/*分割线*/
    public ConfigDialogUtils(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public ConfigDialogUtils builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_config, null);

        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
        // 获取自定义Dialog布局中的控件
        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_content =  (TextView) view.findViewById(R.id.txt_content);
        btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_right = (Button) view.findViewById(R.id.btn_right);
        img_line = view.findViewById(R.id.img_line);
        initClick();
        // 定义Dialog布局和参数
         dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        lLayout_content.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(view);
        return this;
    }
    public ConfigDialogUtils builder(boolean cancel) {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_config, null);

        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
        // 获取自定义Dialog布局中的控件
        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_content =  (TextView) view.findViewById(R.id.txt_content);
        btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_right = (Button) view.findViewById(R.id.btn_right);
        img_line = view.findViewById(R.id.img_line);
        initClick();
        // 定义Dialog布局和参数
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancel);
        lLayout_content.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(view);
        return this;
    }
    /**
     * 设置标题显示样式
     */
    public ConfigDialogUtils setTitleVis(boolean isVis){
        if(isVis){
            txt_title.setVisibility(View.VISIBLE);
        }else{
            txt_title.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置标题字体颜色
     */
    public ConfigDialogUtils setTitleColor(int color){
        txt_title.setTextColor(color);
        return this;
    }
    /**
     * 设置标题栏背景颜色
     */
    public ConfigDialogUtils setTitleBgColor(int bgcolor){
        txt_title.setBackgroundResource(bgcolor);
        return this;
    }
    /**
     * 设置标题的内容
     */
    public ConfigDialogUtils setTitle(int title){
        txt_title.setText(title);
        return this;
    }
    /**
     * 设置标题的内容
     */
    public ConfigDialogUtils setTitle(String title){
        txt_title.setText(title);
        return this;
    }
    /**
     * 设置内容颜色
    */
    public ConfigDialogUtils setContentColr(int color){
        txt_content.setTextColor(color);
        return this;
    }
    /**
     * 设置内容背景颜色
     */
    public ConfigDialogUtils setContentBgColr(int bgcolor){
        txt_content.setBackgroundResource(bgcolor);
        return this;
    }
    /**
     * 设置内容的内容
    */
    public ConfigDialogUtils setContent(int content){
        txt_content.setText(content);
        return this;
    }
    public ConfigDialogUtils setContent(String content){
        txt_content.setText(content);
        return this;
    }
    /**
     * 设置左侧按钮文字颜色
    */
    public ConfigDialogUtils setLeftColor(int leftColor){
        btn_left.setTextColor(leftColor);
        return this;
    }
    /**
     * 设置左侧按钮文字
     */
    public ConfigDialogUtils setLeft(int leftString){
        btn_left.setText(leftString);
        return this;
    }
    public ConfigDialogUtils setLeft(String leftString){
        btn_left.setText(leftString);
        return this;
    }
    /**
     * 设置左侧是否显示
     */
    public ConfigDialogUtils setLeftVis(boolean isVis){
        if(isVis){
            btn_left.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
        }else{
            btn_left.setVisibility(View.GONE);
            img_line.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 设置右侧按钮文字颜色
     */
    public ConfigDialogUtils setRightColor(int rightColor){
        btn_right.setTextColor(mContext.getResources().getColor(rightColor));
        return this;
    }
    /**
     * 设置右侧按钮文字
     */
    public ConfigDialogUtils setRight(int rightString){
        btn_right.setText(rightString);
        return this;
    }
    public ConfigDialogUtils setRight(String rightString){
        btn_right.setText(rightString);
        return this;
    }
    /**
     * 设置右侧是否显示
     */
    public ConfigDialogUtils setRightVis(boolean isVis){
        if(isVis){
            btn_right.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
        }else{
            btn_right.setVisibility(View.GONE);
            img_line.setVisibility(View.GONE);
        }
        return this;
    }
    /**
     * 显示
     */
    public void show(){
        if(dialog!=null&&(!dialog.isShowing())){
            dialog.show();
        }
    }
    private void initClick(){
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftBack!=null){
                    leftBack.leftBtn(what);
                }
                dialog.dismiss();
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightBack!=null){
                    rightBack.rightBtn(what);
                }
                dialog.dismiss();
            }
        });
    }
    public LeftCallBack getLeftBack() {
        return leftBack;
    }

    public ConfigDialogUtils setLeftBack(LeftCallBack leftBack) {
        this.leftBack = leftBack;
        return this;
    }

    public RightCallBack getRightBack() {
        return rightBack;
    }

    public ConfigDialogUtils setRightBack(RightCallBack rightBack) {
        this.rightBack = rightBack;
        return this;
    }

    public LeftCallBack leftBack;
    public interface LeftCallBack{
        void leftBtn(int what);
    }

    public RightCallBack rightBack;
    public interface RightCallBack{
        void rightBtn(int what);
    }
}
