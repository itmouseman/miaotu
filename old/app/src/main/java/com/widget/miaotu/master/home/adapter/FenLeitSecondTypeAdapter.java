package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.bean.SendDemandSeedBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FenLeitSecondTypeAdapter extends BaseQuickAdapter<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean, BaseViewHolder> {
    private final Context mContext;

    public FenLeitSecondTypeAdapter(Context context, int layoutResId, @Nullable List<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean classifySecondsBean) {

        baseViewHolder.setText(R.id.tv_position_list2, classifySecondsBean.getName() + "");
        ShapeTextView tv = (ShapeTextView) baseViewHolder.getView(R.id.tv_position_list2);
        if ("1".equals(classifySecondsBean.getIsChose())) {
            tv.setSolidColor(Color.parseColor("#D3FDF9"));
            tv.setTextColor(Color.parseColor("#FF00CAB8"));
            tv.setStrokeColor(Color.parseColor("#00CAB8"));
            tv.setStrokeWidth(1);


        } else {
            tv.setSolidColor(Color.parseColor("#FFFFFF"));
            tv.setTextColor(Color.parseColor("#333333"));
            tv.setStrokeColor(Color.parseColor("#E2E4EA"));
            tv.setStrokeWidth(1);

        }
    }
}
