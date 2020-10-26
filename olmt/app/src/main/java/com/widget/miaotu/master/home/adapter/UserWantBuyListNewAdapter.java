package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.WantBuySeedListGetBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class UserWantBuyListNewAdapter extends CommonAdapter<WantBuySeedListGetBean> {

    private String type;

    public UserWantBuyListNewAdapter(Context context, int layoutId, List<WantBuySeedListGetBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WantBuySeedListGetBean item, int position) {

        holder.setText(R.id.textView141, item.getSeedlingName())
                .setVisible(R.id.textView142, !StringUtils.isEmpty(item.getPlantType()))
                .setText(R.id.textView142, item.getPlantType())
//                .setText(R.id.textView147, (StringUtils.isEmpty(item.getOfferNum()) ? Html.fromHtml("<font color=\"#A9A9A9\">暂无报价</font>")
//                        : Html.fromHtml("<font color=\"#FF0000\">" + item.getOfferNum() + "</font>" + "人已报价")))
                .setText(R.id.textView146_1, "用苗地：" + item.getProvince() + "  " + item.getCity())
                .setText(R.id.textView194, String.valueOf(item.getOfferNum()))
                .setText(R.id.textView143, item.getWantBuyNum() + "");
        String stext = "";
        try {
            JSONArray jsonArray = new JSONArray(item.getSpec());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String specName = jsonObject.getString("specName");
                String interval = jsonObject.getString("interval");
                String unit = jsonObject.getString("unit");
                if (interval.contains("-")) {
                    if (interval.substring(0, interval.indexOf("-")).equals(interval.substring(interval.indexOf("-") + 1))) {
                        interval = interval.substring(0, interval.indexOf("-"));
                    }
                }
                if (i == 0) {
                    stext = specName + "\t\t" + interval + unit;
                }
                if (i == 1) {
                    stext = stext + " | " + specName + "\t\t" + interval + unit;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AndroidUtils.isNullOrEmpty(item.getQuality())) {
            holder.setText(R.id.textView144, stext + "");
        } else {
            holder.setText(R.id.textView144, "品质\t\t" + item.getQuality() + " | " + stext);
        }
        if ("3".equals(type)) {
            holder.setText(R.id.textView146, item.getTimeInterval() + "");
            if (0 == item.getStatus()) {
                holder.setVisible(R.id.imageView68, false);
            } else {
                holder.getView(R.id.imageView68).setBackgroundResource(R.mipmap.icon_qg_wd_lb_ysx_new);
                holder.setVisible(R.id.imageView68, true);
                holder.setTextColor(R.id.textView147, Color.parseColor("#FF999999"));
                holder.setTextColor(R.id.textView143, Color.parseColor("#FF999999"));
            }
        } else {
            if ("5".equals(type)) {
                if (1 == item.getIsOffered()) {
                    holder.setTextColor(R.id.textView148, Color.parseColor("#FF999999"));
                    holder.setText(R.id.textView148, "已报价");
                } else {
                    holder.setTextColor(R.id.textView148, Color.parseColor("#FF00CAB8") );
                    holder.setText(R.id.textView148, "去报价");
                }
            }
            String str1 = "";
            String str2 = "";
            if (!AndroidUtils.isNullOrEmpty(item.getCreateTime()) && item.getCreateTime().contains("-")) {
                str1 = item.getCreateTime().replaceAll("-", ".");
            }
            if (!AndroidUtils.isNullOrEmpty(item.getDueTime()) && item.getDueTime().contains("-")) {
                str2 = item.getDueTime().replaceAll("-", ".");
            }
            holder.setText(R.id.textView146, str1 + "-" + str2);
            if ("2".equals(type)) {
                holder.getView(R.id.imageView68).setBackgroundResource(R.mipmap.icon_qg_wd_lb_yjs_new);
                holder.setVisible(R.id.imageView68, true);
                holder.setTextColor(R.id.textView147, Color.parseColor("#FF999999"));
                holder.setTextColor(R.id.textView143, Color.parseColor("#FF999999"));
            } else {
                holder.setVisible(R.id.imageView68, false);
            }
        }

        if (AndroidUtils.isNullOrEmpty(item.getCompanyName())) {
            holder.setText(R.id.tv_garden_name, "个人");
        } else {
            holder.setText(R.id.tv_garden_name, item.getCompanyName() + "");
        }

        holder.getView(R.id.tv_edit_want_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("id", String.valueOf(item.getWantBuyId()));
//                intent.setClass(mContext, UserPurchaseSendActivity.class);
//                mContext.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("id", item.getWantBuyId() + "");
//                intent.putExtra("sid", item.getSeedlingId() + "");
//                intent.putExtra("type", type);
////                intent.setClass(mContext, UserPurchaseDetailActivity.class);
//                intent.setClass(mContext, UserPurchaseDetailNewActivity.class);
//                mContext.startActivity(intent);
            }
        });
    }

    public void setOtherData(String type) {
        this.type = type;
    }
}
