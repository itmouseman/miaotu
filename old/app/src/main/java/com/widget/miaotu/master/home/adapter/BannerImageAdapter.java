package com.widget.miaotu.master.home.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.http.bean.HomeInitJavaBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class BannerImageAdapter extends BannerAdapter<HomeInitJavaBean.BannerBean, BannerImageAdapter.BannerViewHolder> {


    private final BannerClickCallBack bannerClickCallBack;

    public BannerImageAdapter(List<HomeInitJavaBean.BannerBean> datas, BannerClickCallBack  bannerClickCallBack) {
        super(datas);
        this.bannerClickCallBack = bannerClickCallBack;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, HomeInitJavaBean.BannerBean data, int position, int size) {

        GlideUtils.loadUrl(holder.imageView.getContext(),data.getCover(),holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bannerClickCallBack!=null){
                    bannerClickCallBack.jumpWeb(data);
                }
            }
        });
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }


    public  interface  BannerClickCallBack{
        void jumpWeb(HomeInitJavaBean.BannerBean bannerBean);
    }
}