package com.ys.module.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 作者：karl.wei
 * 创建日期： 2017/8/17 0017 10:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍: RecyclerView的分割线样式
 */
public class SimpleSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int mTBSpace;
    private int mLRSpace;
    private boolean mTop, mBottom, mLeft, mRight;

    public SimpleSpacesItemDecoration(int tbSpace, boolean top, boolean bottom, boolean left, boolean right) {
        this.mTBSpace = tbSpace;
        this.mTop = top;
        this.mBottom = bottom;
        this.mLeft = left;
        this.mRight = right;
    }

    public SimpleSpacesItemDecoration(int tbSpace, int lrSpace, boolean top, boolean bottom, boolean left, boolean right) {
        this.mTBSpace = tbSpace;
        this.mLRSpace = lrSpace;
        this.mTop = top;
        this.mBottom = bottom;
        this.mLeft = left;
        this.mRight = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //注释这两行是为了上下间距相同
//        if(parent.getChildAdapterPosition(view)==0){
//        }
        if (mTop) {
            outRect.top = mTBSpace;
        }
        if (mBottom) {
            outRect.bottom = mTBSpace;
        }
        if (mLeft) {
            outRect.left = mLRSpace;
        }
        if (mRight) {
            outRect.right = mLRSpace;
        }
    }
}
