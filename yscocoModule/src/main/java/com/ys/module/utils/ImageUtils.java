package com.ys.module.utils;/**
 * Created by Administrator on 2017/9/19 0019.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ys.module.R;
import com.ys.module.log.LogUtils;

import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.decode.ImageAttrs;
import me.panpf.sketch.process.CircleImageProcessor;
import me.panpf.sketch.request.CancelCause;
import me.panpf.sketch.request.DisplayListener;
import me.panpf.sketch.request.ErrorCause;
import me.panpf.sketch.request.ImageFrom;

/**
 * 作者：karl.wei
 * 创建日期： 2017/9/19 0019 16:03
 * 邮箱：karl.wei@yscoco.com
 * 类介绍：图片加载
 */
public class ImageUtils {
    public static void loadHead(Context context, String url, ImageView iv_head) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(R.mipmap.ico_head_def)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url+"").apply(options).into(iv_head);
//        loadHead(context, url, iv_head, R.mipmap.ico_head_def);
    }


    public static void loadHead(Context context, String url, final SketchImageView iv_head, final int defHead) {
        iv_head.getOptions().setProcessor(CircleImageProcessor.getInstance());
        if (TextUtils.isEmpty(url)) {
            iv_head.displayResourceImage(defHead);
            return;
        }
        iv_head.setDisplayListener(new DisplayListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(@NonNull Drawable drawable, @NonNull ImageFrom imageFrom, @NonNull ImageAttrs imageAttrs) {

            }

            @Override
            public void onError(@NonNull ErrorCause cause) {
                iv_head.displayResourceImage(defHead);
            }

            @Override
            public void onCanceled(@NonNull CancelCause cause) {

            }
        });
        iv_head.displayImage(url);
    }


    public static void loadHead(Context context, String url, ImageView iv_head, int defHead) {
        LogUtils.e("debug===url====>" + url);
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(defHead)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url + "").apply(options).into(iv_head);
    }

    public static void showImage(Context context, ImageView iv, String url, int defId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(defId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

}
