package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class NurseryTreeListAdapter extends BaseQuickAdapter<SeedListGetBean, BaseViewHolder> {


    private final Context mContext;

    public NurseryTreeListAdapter(Context context, int layoutResId, @Nullable List<SeedListGetBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, SeedListGetBean item) {
        if (!AndroidUtils.isNullOrEmpty(item.getSeedlingUrls())) {
            Gson gson = new Gson();
            List<NewsInfoCoverBean> mData = gson.fromJson(item.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
            }.getType());

            if (mData != null && mData.size() > 0)
                Glide.with(mContext).load(mData.get(0).getT_url()).into((ImageView) holder.getView(R.id.niv_demand_list_avatar));
        }

        holder.setText(R.id.tv_demand_list_nickname, item.getSeedlingName() + "")
                .setText(R.id.tv_demand_list_nick_stock, "库存 " + item.getRepertory() + "")
                .setText(R.id.tv_demand_list_address, item.getProvince() + " " + item.getCity() + "")
                .setText(R.id.tv_garden_name, item.getGardenName() + "");

        if (TextUtils.isEmpty(item.getPlantType())) {
            holder.getView(R.id.tv_demand_list_nickname_type).setVisibility(View.GONE);

        }else {
            holder.getView(R.id.tv_demand_list_nickname_type).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_demand_list_nickname_type, item.getPlantType() + "");

        }



//        if (AndroidUtils.isNullOrEmpty(item.getGardenName())) {
////            helper.setVisible(R.id.ll_garden_name,false);
//            holder.setText(R.id.tv_garden_name, item.getCompanyName() + "");
//        } else {
//            holder.setVisible(R.id.ll_garden_name, true);
//        }
        holder.getView(R.id.ll_garden_name).setVisibility(View.GONE);
        if ("1".equals(item.getQuality())) {
            holder.setVisible(R.id.tv_quality, true);
            holder.setText(R.id.tv_quality, "精品");
            holder.getView(R.id.tv_quality).setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_demand_4));
        } else if ("0".equals(item.getQuality())) {
            holder.setVisible(R.id.tv_quality, true);
            holder.setText(R.id.tv_quality, "清货");
            holder.getView(R.id.tv_quality).setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_demand_4));
        } else {
            holder.setVisible(R.id.tv_quality, false);
        }
        if ("1".equals(item.getIsPromote())) {
            holder.setVisible(R.id.tv_promote, true);
        } else {
            holder.setVisible(R.id.tv_promote, false);
        }

        if (0 == item.getStatus()) {
            holder.setVisible(R.id.tv_demand_review, true);
            holder.setVisible(R.id.imageView68, false);
        } else if (3 == item.getStatus()) {
            holder.setVisible(R.id.tv_demand_review, false);
            holder.setVisible(R.id.imageView68, true);
        } else {
            holder.setVisible(R.id.tv_demand_review, false);
            holder.setVisible(R.id.imageView68, false);
        }
        if (1 == item.getStatus()) {
            if ("1".equals(item.getIsShow())) {
                holder.setVisible(R.id.tv_demand_review, true);
                if ("1".equals(item.getIsPromote())) {
                    holder.setText(R.id.tv_demand_review, "取消推广");
                    holder.setTextColor(R.id.tv_demand_review, mContext.getResources().getColor(R.color.text_color_66));
                } else {
                    holder.setText(R.id.tv_demand_review, "开通推广");
                    holder.setTextColor(R.id.tv_demand_review, mContext.getResources().getColor(R.color.holo_red_light));
                }
            } else {
                holder.setVisible(R.id.tv_demand_review, false);
            }
        }
        if (AndroidUtils.isNullOrEmpty(item.getPrice()) || "0".equals(item.getPrice()) || "0.0".equals(item.getPrice()) || "0.00".equals(item.getPrice())) {
            holder.setText(R.id.tv_demand_list_nick_price, "面议");
        } else {
            holder.setText(R.id.tv_demand_list_nick_price, "￥" + item.getPrice() + "/株");
        }
        //解析苗木属性json赋值 tv_demand_list_nick_high,tv_demand_list_nick_crown 冠
        try {
            JSONArray jsonArray = new JSONArray(item.getSpec());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String specName = jsonObject.getString("specName");
                String interval = jsonObject.getString("interval");
                String unit = jsonObject.getString("unit");
                if ("厘米".equals(unit)) {
                    unit = "cm";
                } else if ("米".equals(unit)) {
                    unit = "m";
                }
                if (interval.contains("-")) {
                    if (interval.substring(0, interval.indexOf("-")).equals(interval.substring(interval.indexOf("-") + 1))) {
                        interval = interval.substring(0, interval.indexOf("-"));
                    }
                }
                if (i == 0) {
                    holder.setText(R.id.tv_demand_list_nick_high, specName + " " + interval + unit);
                }
                if (i == 1) {
                    holder.setText(R.id.tv_demand_list_nick_crown, specName + " " + interval + unit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.getView(R.id.tv_demand_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求
//                if ("1".equals(item.getIsShow())) {
//                    if ("1".equals(item.getIsPromote())) {
//                        JSONObject request = new  JSONObject();
//                        request.put("id", item.getId());
//                        request.put("status", "0");
//                        RxHttpUtils.createApi(ApiService.class)
//                                .promoteSeed(request)
//                                .compose(Transformer.switchSchedulers())
//                                .subscribe(new DataObserverNew<String>() {
//                                    @Override
//                                    protected void onError(String errorMsg) {
//                                        ToastUtils.showLong(errorMsg + "");
//                                    }
//
//                                    @Override
//                                    protected void onSuccess(String data) {
//                                        item.setIsPromote("0");
//                                        holder.setVisible(R.id.tv_promote, false);
//                                        holder.setText(R.id.tv_demand_review, "开通推广");
//                                        holder.setTextColor(R.id.tv_demand_review, mContext.getResources().getColor(R.color.holo_red_light));
//                                    }
//                                });
//                    } else {
//                        com.alibaba.fastjson.JSONObject request = new com.alibaba.fastjson.JSONObject();
//                        request.put("id", item.getId());
//                        request.put("status", "1");
//                        RxHttpUtils.createApi(ApiService.class)
//                                .promoteSeed(request)
//                                .compose(Transformer.switchSchedulers())
//                                .subscribe(new DataObserverNew<String>() {
//                                    @Override
//                                    protected void onError(String errorMsg) {
//                                        ToastUtils.showLong(errorMsg + "");
//                                    }
//
//                                    @Override
//                                    protected void onSuccess(String data) {
//                                        item.setIsPromote("1");
//                                        holder.setVisible(R.id.tv_promote, true);
//                                        holder.setText(R.id.tv_demand_review, "取消推广");
//                                        holder.setTextColor(R.id.tv_demand_review, mContext.getResources().getColor(R.color.text_color_66));
//                                    }
//                                });
//                    }
//                }
            }
        });

//        holder.itemView.setOnClickListener(v -> {
//
//            String[] key = {"comFrom", SPConstant.TRANSTENT_CONTENT};
//            String[] value = {"1",item.getId() + ""};
//            IntentUtils.startIntent(mContext, HomeTgAndMmActivity.class, key, value);
//        });
    }
}
