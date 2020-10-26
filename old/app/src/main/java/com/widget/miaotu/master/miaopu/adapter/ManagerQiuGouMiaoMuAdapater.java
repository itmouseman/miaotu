package com.widget.miaotu.master.miaopu.adapter;

import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SeedListGetBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManagerQiuGouMiaoMuAdapater extends BaseQuickAdapter<SeedListGetBean, BaseViewHolder> {

    public ManagerQiuGouMiaoMuAdapater(int layoutResId, @Nullable List<SeedListGetBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SeedListGetBean seedListGetBean) {
        baseViewHolder.setText(R.id.tv_item_manager_qiugou1, seedListGetBean.getSeedlingName());
        baseViewHolder.setText(R.id.stv_item_manager_qiugou2, seedListGetBean.getPlantType());

        baseViewHolder.setText(R.id.tv_item_manager_qiugou4, AndroidUtils.isNullOrEmpty(seedListGetBean.getPrice()) || "0".equals(seedListGetBean.getPrice()) || "0.0".equals(seedListGetBean.getPrice()) || "0.00".equals(seedListGetBean.getPrice()) ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedListGetBean.getPrice() + "</big></big></font>/株"));
        baseViewHolder.setText(R.id.tv_item_manager_qiugou5, "库存 "+seedListGetBean.getRepertory());




        List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = new Gson().fromJson(seedListGetBean.getSpec(), new TypeToken<List<HomeMmSpecJavaBean>>() {
        }.getType());

        String mStringSpecName = null;
        if (homeMmSpecJavaBeanList.size() > 1) {
            mStringSpecName = homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                    + " | " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit();
        } else if (homeMmSpecJavaBeanList.size() == 1) {
            mStringSpecName = homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit();
        }
        baseViewHolder.setText(R.id.tv_item_manager_qiugou3, mStringSpecName);
        //是否推广
        if (seedListGetBean.getIsPromote() == 1) {
            baseViewHolder.getView(R.id.stv_item_manager_qiugou6).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_item_manager_qiugou7, "取消推广");
            ((TextView) baseViewHolder.getView(R.id.tv_item_manager_qiugou7)).setTextColor(Color.parseColor("#666666"));
        } else {
            baseViewHolder.getView(R.id.stv_item_manager_qiugou6).setVisibility(View.GONE);
            baseViewHolder.setText(R.id.tv_item_manager_qiugou7, "开通推广");
            ((TextView) baseViewHolder.getView(R.id.tv_item_manager_qiugou7)).setTextColor(Color.parseColor("#FD522B"));
        }
        if (!AndroidUtils.isNullOrEmpty(seedListGetBean.getSeedlingUrls())) {
            Gson gson = new Gson();
            List<NewsInfoCoverBean> mData = gson.fromJson(seedListGetBean.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
            }.getType());

            if (mData != null && mData.size() > 0)
                Glide.with(getContext()).load(mData.get(0).getT_url()).into((ImageView) baseViewHolder.getView(R.id.iv_item_manager_qiugou));
        }


    }
}
