package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;




/**
 * @author jiefu
 */
public class SeedlingAdapter extends BaseQuickAdapter<SeedlingInfo, BaseViewHolder> {

    public SeedlingAdapter(@Nullable List<SeedlingInfo> data) {
        super(R.layout.item_recyclerview_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SeedlingInfo item) {
        helper.setText(R.id.tv_item_text, item.getBaseName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckListener.Click(item);
            }
        });
    }

    public interface OnCheckListener {
        void Click(SeedlingInfo info);
    }

    public OnCheckListener onCheckListener;

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }
}
