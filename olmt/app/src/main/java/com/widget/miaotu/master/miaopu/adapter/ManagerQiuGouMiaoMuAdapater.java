package com.widget.miaotu.master.miaopu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.head.HeadStatusBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManagerQiuGouMiaoMuAdapater extends BaseQuickAdapter<SeedListGetBean, BaseViewHolder> {


    private final Context mContext;

    public ManagerQiuGouMiaoMuAdapater(Context context, int layoutResId, @Nullable List<SeedListGetBean> data) {
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SeedListGetBean seedListGetBean) {
        baseViewHolder.setText(R.id.tv_item_manager_qiugou1, seedListGetBean.getSeedlingName());
        baseViewHolder.setText(R.id.stv_item_manager_qiugou2, seedListGetBean.getPlantType());

//        baseViewHolder.setText(R.id.tv_item_manager_qiugou4, AndroidUtils.isNullOrEmpty(seedListGetBean.getPrice()) || "0".equals(seedListGetBean.getPrice()) || "0.0".equals(seedListGetBean.getPrice()) || "0.00".equals(seedListGetBean.getPrice()) ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedListGetBean.getPrice() + "</big></big></font>/株"));
        baseViewHolder.setText(R.id.tv_item_manager_qiugou4, AndroidUtils.isNullOrEmpty(seedListGetBean.getPrice()) || "0".equals(seedListGetBean.getPrice()) || "0.0".equals(seedListGetBean.getPrice()) || "0.00".equals(seedListGetBean.getPrice()) ? "面议" : seedListGetBean.getPrice());

        baseViewHolder.setText(R.id.tv_item_manager_qiugou5, "库存 " + seedListGetBean.getRepertory());


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
            baseViewHolder.getView(R.id.tv_item_manager_qiugou7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    promoteSeedling(String.valueOf(seedListGetBean.getId()), "0", baseViewHolder.getAdapterPosition(), seedListGetBean);
                }
            });

        } else {
            baseViewHolder.getView(R.id.stv_item_manager_qiugou6).setVisibility(View.GONE);
            baseViewHolder.setText(R.id.tv_item_manager_qiugou7, "开通推广");
            ((TextView) baseViewHolder.getView(R.id.tv_item_manager_qiugou7)).setTextColor(Color.parseColor("#FD522B"));

            baseViewHolder.getView(R.id.tv_item_manager_qiugou7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    promoteSeedling(String.valueOf(seedListGetBean.getId()), "1", baseViewHolder.getAdapterPosition(), seedListGetBean);
                }
            });
        }
        if (!AndroidUtils.isNullOrEmpty(seedListGetBean.getSeedlingUrls())) {
            Gson gson = new Gson();
            List<NewsInfoCoverBean> mData = gson.fromJson(seedListGetBean.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
            }.getType());

            if (mData != null && mData.size() > 0)
                Glide.with(getContext()).load(mData.get(0).getT_url()).into((ImageView) baseViewHolder.getView(R.id.iv_item_manager_qiugou));
        }

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("comFrom", "0");
                intent.putExtra(SPConstant.TRANSTENT_CONTENT, seedListGetBean.getId() + "");
                intent.setClass(mContext, HomeTgAndMmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }


    //	登记苗木id   	推广标识；0：取消推广；1：推广
    private void promoteSeedling(String id, String status, int position, SeedListGetBean seedListGetBean) {
        RetrofitFactory.getInstence().API().promoteSeedling(new HeadStatusBean(id, status))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                Logger.e(t.getData().toString());
                if (t.getStatus() == 100) {
                    ToastUtils.showShort("操作成功");
                    if (status.equals("0")) {
                        seedListGetBean.setIsPromote(2);
                    } else if (status.equals("1")) {
                        seedListGetBean.setIsPromote(1);
                    }
                    notifyItemChanged(position);
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showLong("网络异常！");
            }
        });
    }
}
