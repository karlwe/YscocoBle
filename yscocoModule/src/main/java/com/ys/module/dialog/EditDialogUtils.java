package com.ys.module.dialog;/**
 * Created by Administrator on 2017/8/9 0009.
 */

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.module.R;
import com.ys.module.utils.StringUtils;

/**
 * 作者：karl.wei
 * 创建日期： 2017/8/9 0009 14:11
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：常见的弹出框，可以选择确定取消，提醒
 */
public class EditDialogUtils {
    private Context mContext;
    private Display display;
    private Dialog dialog;
    private int what = 0;/*返回的内容*/
    LinearLayout lLayout_content;/*整个父控件*/
    private TextView txt_title;/*标题栏*/
    private EditText txt_content;/*内容*/
    private TextView tv_error_hint ;/*错误提示*/
    private Button btn_left;/*左侧按钮*/
    private Button btn_right;/*右侧按钮*/
    private View img_line;/*分割线*/
    public EditDialogUtils(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public EditDialogUtils builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_edit_info, null);

        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
        // 获取自定义Dialog布局中的控件
        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_content =  (EditText) view.findViewById(R.id.et_content);
        btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_right = (Button) view.findViewById(R.id.btn_right);
        tv_error_hint = (TextView) view.findViewById(R.id.tv_error_hint);
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
    /**
     * 设置标题显示样式
     */
    public EditDialogUtils setTitleVis(boolean isVis){
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
    public EditDialogUtils setTitleColor(int color){
        txt_title.setTextColor(color);
        return this;
    }
    /**
     * 设置标题栏背景颜色
     */
    public EditDialogUtils setTitleBgColor(int bgcolor){
        txt_title.setBackgroundResource(bgcolor);
        return this;
    }
    /**
     * 设置标题的内容
     */
    public EditDialogUtils setTitle(int title){
        txt_title.setText(title);
        return this;
    }
    /**
     * 设置标题的内容
     */
    public EditDialogUtils setTitle(String title){
        txt_title.setText(title);
        return this;
    }
    /**
     * 设置内容颜色
    */
    public EditDialogUtils setContentColr(int color){
        txt_content.setTextColor(color);
        return this;
    }
    /**
     * 设置内容背景颜色
     */
    public EditDialogUtils setContentBgColr(int bgcolor){
        txt_content.setBackgroundResource(bgcolor);
        return this;
    }
    /**
     * 设置提示的内容
     */
    public EditDialogUtils setHintContent(int content){
        txt_content.setHint(content);
        return this;
    }
    public EditDialogUtils setHintContent(String content){
        txt_content.setText(content);
        return this;
    }
    /**
     * 设置输入内容
     */
    public EditDialogUtils setType(int type){
        txt_content.setInputType(type);
        return this;
    }
    /*设置输入的限制字符*/
    public EditDialogUtils setDigits(String digits){
        txt_content.setKeyListener(DigitsKeyListener.getInstance(digits));
        return this;
    }
    /**
     * 设置输入框的长度
     */
    public EditDialogUtils setMaxLength(int length){
        txt_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        return this;
    }
    /**
     * 设置内容的内容
    */
    public EditDialogUtils setContent(int content){
        txt_content.setText(content);
        return this;
    }
    public EditDialogUtils setContent(String content){
        txt_content.setText(content);
        return this;
    }
    /**
     * 设置左侧按钮文字颜色
    */
    public EditDialogUtils setLeftColor(int leftColor){
        btn_left.setTextColor(leftColor);
        return this;
    }
    /**
     * 设置左侧按钮文字
     */
    public EditDialogUtils setLeft(int leftString){
        btn_left.setText(leftString);
        return this;
    }
    public EditDialogUtils setLeft(String leftString){
        btn_left.setText(leftString);
        return this;
    }
    /**
     * 设置左侧是否显示
     */
    public EditDialogUtils setLeftVis(boolean isVis){
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
    public EditDialogUtils setRightColor(int rightColor){
        btn_right.setTextColor(rightColor);
        return this;
    }
    /**
     * 设置右侧按钮文字
     */
    public EditDialogUtils setRight(int rightString){
        btn_right.setText(rightString);
        return this;
    }
    public EditDialogUtils setRight(String rightString){
        btn_right.setText(rightString);
        return this;
    }
    /**
     * 设置右侧是否显示
     */
    public EditDialogUtils setRightVis(boolean isVis){
        if(isVis){
            btn_right.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
        }else{
            btn_right.setVisibility(View.GONE);
            img_line.setVisibility(View.GONE);
        }
        return this;
    }
    public void show(){
        if(dialog!=null&&(!dialog.isShowing())){
            dialog.show();
        }
    }
    private void initClick(){
        txt_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_error_hint.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                String inputContent = txt_content.getText().toString().trim();
                if(StringUtils.isEmpty(inputContent)){
                    tv_error_hint.setText(R.string.input_content_text);
                }else {
                    if (rightBack != null) {
                        rightBack.rightBtn(what,inputContent);
                    }
                    dialog.dismiss();
                }
            }
        });
    }
    public LeftCallBack getLeftBack() {
        return leftBack;
    }

    public EditDialogUtils setLeftBack(LeftCallBack leftBack) {
        this.leftBack = leftBack;
        return this;
    }

    public RightCallBack getRightBack() {
        return rightBack;
    }

    public EditDialogUtils setRightBack(RightCallBack rightBack) {
        this.rightBack = rightBack;
        return this;
    }

    public LeftCallBack leftBack;
    public interface LeftCallBack{
        void leftBtn(int what);
    }

    public RightCallBack rightBack;
    public interface RightCallBack{
        void rightBtn(int what, String content);
    }
}
