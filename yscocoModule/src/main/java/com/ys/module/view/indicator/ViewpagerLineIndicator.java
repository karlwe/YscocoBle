package com.ys.module.view.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ys.module.R;


/**
 * 作者：karl.wei
 * 创建日期： 2017/8/17 0017 10:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：线性的ViewPage 指示图标
 */
public class ViewpagerLineIndicator<T extends ViewGroup> extends View
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private int currentPosition = 0;
    private int paddingSize;
    private T view;
    private int currentColor ;
    private int nomalColor ;
    private int lineColor;
    private Paint paint;
    private PageChangeListener pageChangeListener;
    int top = 0;
    //int bottom =15;
    private float pageOffeset = 0;
    private int color;

    public ViewpagerLineIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray =  context.obtainStyledAttributes(attrs, R.styleable.ViewpagerIndicator_);
        currentColor = typedArray.getColor(R.styleable.ViewpagerIndicator__text_checked_color,getResources().getColor(R.color.title_bg_color));
        nomalColor = typedArray.getColor(R.styleable.ViewpagerIndicator__text_nomalColor,Color.GRAY);
        lineColor = typedArray.getColor(R.styleable.ViewpagerIndicator__line_color,getResources().getColor(R.color.title_bg_color));
        typedArray.recycle();

    }

    public ViewpagerLineIndicator(Context context) {
        super(context);
    }

    public void setPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewPager == null) {
            return;
        }

        float lineLeft = 0;
        float lineRight = 0;
        if (currentPosition > viewPager.getAdapter().getCount() - 1) {
            return;
        }
        lineLeft = view.getChildAt(currentPosition).getLeft() + paddingSize;
        lineRight = view.getChildAt(currentPosition).getRight() - paddingSize;

        if (pageOffeset > 0 && currentPosition < viewPager.getAdapter().getCount() - 1) {//3

            View nextTab = view.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft() + paddingSize;
            final float nextTabRight = nextTab.getRight() - paddingSize;

            lineLeft = (pageOffeset * nextTabLeft + (1f - pageOffeset) * lineLeft);
            lineRight = (pageOffeset * nextTabRight + (1f - pageOffeset) * lineRight);
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(lineColor);
        canvas.drawRect(lineLeft, top, lineRight, getHeight(), paint);
    }

    public void setIndicatorView(T view) {
        this.view = view;
        ViewGroup viewGroup = view;
        View childView = viewGroup.getChildAt(viewPager.getCurrentItem());
        if (childView instanceof TextView) {
            TextView textView = (TextView) childView;
            textView.setTextColor(currentColor);
        }

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            v.setTag(i);
            v.setOnClickListener(this);
        }

        invalidate();
    }

    public void setViewPager(ViewPager pager) {
        this.viewPager = pager;
        currentPosition = viewPager.getCurrentItem();
        viewPager.addOnPageChangeListener(this);
        invalidate();
    }

    public void setPading(int padingSize) {
        this.paddingSize = padingSize;
        invalidate();
    }

    public void setColor(int color) {
        this.currentColor = color;
        invalidate();
    }

    public void setNomalColor(int color) {

        this.nomalColor = color;
    }

    public void setLineColor(int color){
        this.lineColor = color;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

        if (null != pageChangeListener) {

            pageChangeListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        currentPosition = arg0;
        pageOffeset = arg1;
        invalidate();
        if (null != pageChangeListener) {
            pageChangeListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            ViewGroup viewGroup = view;
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof TextView) {
                TextView textView = (TextView) childView;
                if (arg0 == i) {
                    textView.setTextColor(currentColor);
                } else {
                    textView.setTextColor(nomalColor);
                }
            }
        }

        if (null != pageChangeListener) {
            pageChangeListener.onPageSelected(arg0);
        }
    }

    @Override
    public void onClick(View v) {

        int tag = (Integer) v.getTag();

        int currentSelectItem = viewPager.getCurrentItem();

        if (tag == currentSelectItem) {

        } else {

            viewPager.setCurrentItem(tag);
        }
    }

    public interface PageChangeListener {

        public void onPageScrollStateChanged(int arg0);

        public void onPageScrolled(int arg0, float arg1, int arg2);

        public void onPageSelected(int arg0);
    }
}
