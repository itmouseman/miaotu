package com.widget.miaotu.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.widget.miaotu.R;

/**
 * Created by jbwl on 2018/11/24 14:19.
 */

public class GlideUtils {

//
//    /**
//     * 圆形
//     *
//     * @param context
//     * @param url
//     * @param iv
//     */
//    public static void circleTransform(Context context, String url, ImageView iv) {
//        if (context != null){
//            RequestOptions requestOptions = RequestOptions.circleCropTransform()
//                    .error(R.drawable.ic_logo);
//            if (TextUtils.isEmpty(url)) {
//                Glide.with(context).load(R.drawable.ic_logo)
//                        .apply(requestOptions)
//                        .into(iv);
//            } else {
//                Glide.with(context).load(url)
//                        .apply(requestOptions)
//                        .into(iv);
//            }
//        }
//
//    }
//
//    /**
//     * 照片墙头像： url 来源：远端+ 本地
//     * @param context
//     * @param url
//     * @param iv
//     */
//    public static void wallCircleTransform(Context context, String url, ImageView iv) {
//        RequestOptions requestOptions = RequestOptions.circleCropTransform()
//                .error(R.drawable.ic_logo);
//        if (TextUtils.isEmpty(url)) {
//            if (url.startsWith("http")){
//                Glide.with(context).load(R.drawable.ic_logo)
//                        .apply(requestOptions)
//                        .into(iv);
//            }else {
//                Glide.with(context).load(url).apply(requestOptions).into(iv);
//            }
//
//        } else {
//            Glide.with(context).load(url)
//                    .apply(requestOptions)
//                    .into(iv);
//        }
//    }
//
//    /**
//     * 圆角
//     *
//     * @param context
//     * @param url
//     * @param iv
//     * @param corner
//     */
//    public static void cornerTransform(Context context, String url, ImageView iv, int corner) {
//        RequestOptions requestOptions = new RequestOptions()
//                .transforms(new GlideRoundTransform(context, corner))
//                .error(R.drawable.ic_logo);
//        if (TextUtils.isEmpty(url)) {
//            Glide.with(context).load(R.drawable.ic_logo).apply(requestOptions).into(iv);
//        } else {
//            Glide.with(context).load(url).apply(requestOptions).into(iv);
//        }
//    }
//
//    public static void cornerTransform(Context context, File file, ImageView iv, int corner) {
//        RequestOptions requestOptions = new RequestOptions()
//                .transforms(new GlideRoundTransform(context, corner))
//                .error(R.drawable.ic_logo);
//        Glide.with(context).load(file).apply(requestOptions).into(iv);
//
//    }

    public static void loadUrl(Context context, String url, ImageView iv ) {


        if (TextUtils.isEmpty(url)) {
//            Glide.with(context)
//                    .load(R.drawable.zhanweitu_150_150)
//                    .placeholder(R.drawable.zhanweitu_150_150)
//                    .error(R.drawable.zhanweitu_150_150)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
////                    .override(weight, height)//指定图片大小
//                    .centerCrop()
//                    .into(iv);
            Glide.with(context)
                    .load(R.drawable.zhanweitu_150_150).into(iv);
        } else {
            Glide.with(context)
                    .load(url).into(iv);
//            Glide.with(context)
//                    .load(url)
//                    .placeholder(R.drawable.zhanweitu_150_150)
//                    .error(R.drawable.zhanweitu_150_150)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
////                    .override(weight, height)//指定图片大小
//                    .into(iv);
        }


    }
}
