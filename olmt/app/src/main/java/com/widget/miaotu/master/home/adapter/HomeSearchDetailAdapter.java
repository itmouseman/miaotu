package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchDetailAdapter extends BaseQuickAdapter<HomeSearchDetailJavaBean.SeedlingBaseInfosBean, BaseViewHolder> {


    private final Context mContext;

    public HomeSearchDetailAdapter(Context context, int layoutResId, @Nullable List<HomeSearchDetailJavaBean.SeedlingBaseInfosBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeSearchDetailJavaBean.SeedlingBaseInfosBean seedlingBaseInfosBean) {
        String imgUrls = seedlingBaseInfosBean.getSeedlingUrls();

        if (!TextUtils.isEmpty(imgUrls)) {
            Gson gson = new Gson();
            List<ImgUrlJavaBean> imgUrlJavaBeanList = gson.fromJson(imgUrls, new TypeToken<List<ImgUrlJavaBean>>() {
            }.getType());
            GlideUtils.loadUrl(mContext, imgUrlJavaBeanList.get(0).getT_url(), baseViewHolder.getView(R.id.iv_home_search_detail_item));
        }

        baseViewHolder.setText(R.id.tv_home_search_detail_item1, seedlingBaseInfosBean.getSeedlingName());
        if (!TextUtils.isEmpty(seedlingBaseInfosBean.getPlantType())) {
            baseViewHolder.getView(R.id.tv_home_search_detail_item2).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_home_search_detail_item2, seedlingBaseInfosBean.getPlantType());
        } else {
            baseViewHolder.getView(R.id.tv_home_search_detail_item2).setVisibility(View.GONE);
        }

        if (seedlingBaseInfosBean.getQuality() == 1) {//精品
            baseViewHolder.setImageResource(R.id.tv_search_detail_quilt, R.mipmap.iv_home_jinping);
        } else if (seedlingBaseInfosBean.getQuality() == 0) {//清货
            baseViewHolder.setImageResource(R.id.tv_search_detail_quilt, R.mipmap.iv_home_qinghuo);
        }

        Gson gson = new Gson();
        String m = seedlingBaseInfosBean.getSpec().toString().replace("\'", "\"");
        List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(m, new TypeToken<List<HomeMmSpecJavaBean>>() {
        }.getType());
        if (homeMmSpecJavaBeanList.size() > 1) {
            baseViewHolder.setText(R.id.tv_home_search_detail_item3, homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                    + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit());

        } else if (homeMmSpecJavaBeanList.size() == 1) {
            baseViewHolder.setText(R.id.tv_home_search_detail_item3, homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit());

        }


        baseViewHolder.setText(R.id.tv_home_search_detail_item4, "库存" + seedlingBaseInfosBean.getRepertory());
        if (seedlingBaseInfosBean.getIsVip() == 1) {
            baseViewHolder.getView(R.id.tv_home_search_detail_item5).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.tv_home_search_detail_item5).setVisibility(View.GONE);
        }
        if (seedlingBaseInfosBean.getNameAuth() == 1) {
            baseViewHolder.getView(R.id.tv_home_search_detail_item6).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.tv_home_search_detail_item6).setVisibility(View.GONE);
        }


        baseViewHolder.setText(R.id.tv_home_search_detail_item7, seedlingBaseInfosBean.getCompanyName());
        String distance = String.format("%.1f", seedlingBaseInfosBean.getDistance());
        if (TextUtils.isEmpty(seedlingBaseInfosBean.getProvince()) && TextUtils.isEmpty(seedlingBaseInfosBean.getCity())) {
            baseViewHolder.setText(R.id.tv_home_search_detail_item8, distance + "km");
        } else {
            baseViewHolder.setText(R.id.tv_home_search_detail_item8, seedlingBaseInfosBean.getProvince() + seedlingBaseInfosBean.getCity() + "|" + distance + "km");
        }


//        baseViewHolder.setText(R.id.tv_home_search_detail_item9, seedlingBaseInfosBean.getPrice() == 0 ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedlingBaseInfosBean.getPrice() + "</big></big></font>/株"));
        baseViewHolder.setText(R.id.tv_home_search_detail_item9, seedlingBaseInfosBean.getPrice() == 0 ? "面议" : "￥" + seedlingBaseInfosBean.getPrice() + "/株起");


        baseViewHolder.setText(R.id.tv_home_search_detail_item10, " ");

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] key = {"comFrom", SPConstant.TRANSTENT_CONTENT};
                String[] value = {"1", seedlingBaseInfosBean.getId() + ""};
                IntentUtils.startIntent(mContext, HomeTgAndMmActivity.class, key, value);
            }
        });

    }


}
