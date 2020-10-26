package com.widget.miaotu.common.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.widget.miaotu.R;

import java.io.File;

public class ImageLoader implements XPopupImageLoader {
    @Override
    public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
        //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
//        Glide.with(imageView).load(url).override(100, 100).apply(new RequestOptions().placeholder(R.drawable.zhanweitu_345_133).override(Target.SIZE_ORIGINAL)).into(imageView);
        Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.drawable.zhanweitu_345_133).override(Target.SIZE_ORIGINAL)).into(imageView);
//        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @Override
    public File getImageFile(@NonNull Context context, @NonNull Object uri) {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}