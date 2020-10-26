package com.widget.miaotu.common.utils.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.ImageLoader;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public  class RecycleviewAdapterOfpopup extends CommonAdapter<Object> {

    private final List<Object> mimgDatas;

    public RecycleviewAdapterOfpopup(Context context, int layoutId, List<Object> datas) {
        super(context, layoutId, datas);
        mimgDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object url, int position) {
        final ImageView imageView = holder.<ImageView>getView(R.id.image);
        //1. 加载图片, 由于ImageView是centerCrop，必须指定Target.SIZE_ORIGINAL，禁止Glide裁剪图片；
        // 这样我就能拿到原始图片的Matrix，才能有完美的过渡效果
//        Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.drawable.zhanweitu_150_150)
//                .override(Target.SIZE_ORIGINAL))
//                .fitCenter()
//                .into(imageView);
        Glide.with(imageView.getContext()).load(url).into(imageView);
        //2. 设置点击
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(holder.itemView.getContext()).asImageViewer(imageView, position, mimgDatas,
                        new OnSrcViewUpdateListener() {
                            @Override
                            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                                RecyclerView rv = (RecyclerView) holder.itemView.getParent();
                                popupView.updateSrcView((ImageView) rv.getChildAt(position));
                            }
                        }, new ImageLoader())
                        .show();
            }
        });
    }
}