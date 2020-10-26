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


    public static void loadUrl(Context context, String url, ImageView iv ) {


        if (TextUtils.isEmpty(url)) {

            Glide.with(context)
                    .load(R.drawable.zhanweitu_150_150).into(iv);
        } else {
            Glide.with(context)
                    .load(url).into(iv);

        }


    }
}
