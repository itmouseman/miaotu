package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.SendDemandSeedBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreePlantTypeAdapter extends BaseQuickAdapter<SendDemandSeedBean.ClassifyFirstsBean.PlantTypeListsBean, BaseViewHolder> {
    private final Context mContext;

    public TreePlantTypeAdapter(Context context, int layoutResId, @Nullable List<SendDemandSeedBean.ClassifyFirstsBean.PlantTypeListsBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.PlantTypeListsBean classifyFirstsBean) {

        baseViewHolder.setText(R.id.tv_position_list, classifyFirstsBean.getName() + "");
        if ("1".equals(classifyFirstsBean.getIsChose())) {
            baseViewHolder.setTextColor(R.id.tv_position_list, Color.parseColor("#FFFFFF"));
            baseViewHolder.setBackgroundResource(R.id.tv_position_list, R.drawable.bg_green_demand_4);

        } else {
            baseViewHolder.setTextColor(R.id.tv_position_list, Color.parseColor("#000000"));
            baseViewHolder.setBackgroundResource(R.id.tv_position_list, R.drawable.bg_gray_demand_4);
        }
    }
}
