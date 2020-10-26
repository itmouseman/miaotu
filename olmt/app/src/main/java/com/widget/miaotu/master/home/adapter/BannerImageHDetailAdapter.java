package com.widget.miaotu.master.home.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class BannerImageHDetailAdapter extends BannerAdapter<ImgUrlJavaBean, BannerImageHDetailAdapter.BannerDetailViewHolder> {


    private final BannerClicickHDetail mBannerClicickHDetail;
    private final List<ImgUrlJavaBean> mImgDatas;


    public BannerImageHDetailAdapter(List<ImgUrlJavaBean> datas, BannerClicickHDetail bannerClicickHDetail) {
        super(datas);
        mBannerClicickHDetail = bannerClicickHDetail;
        mImgDatas = datas;


    }

    @Override
    public BannerDetailViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerDetailViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerDetailViewHolder holder, ImgUrlJavaBean data, int position, int size) {
        GlideUtils.loadUrl(holder.imageView.getContext(), data.getT_url(), holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBannerClicickHDetail != null) {
                    mBannerClicickHDetail.itemBannerClick(holder,position);
                }
            }
        });

    }


  public   class BannerDetailViewHolder extends RecyclerView.ViewHolder {
     public    ImageView imageView;


        public BannerDetailViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }


    public interface BannerClicickHDetail {
        void itemBannerClick(BannerDetailViewHolder holder,int position);

    }
}
