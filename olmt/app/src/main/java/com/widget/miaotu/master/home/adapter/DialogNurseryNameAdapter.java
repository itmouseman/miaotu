package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.NurseryNameBean;


import java.util.List;

public class DialogNurseryNameAdapter extends BaseAdapter<NurseryNameBean, RecyclerView.ViewHolder> {

    public DialogNurseryNameAdapter(@NonNull Context mContext, @NonNull List<NurseryNameBean> listData, int layoutId) {
        super(mContext, listData, layoutId);
    }

    @Override
    public void onBindViewHolder(com.widget.miaotu.master.home.adapter.BaseViewHolder holder, NurseryNameBean data, int position) {
        TextView title = holder.getView(R.id.tv_nursery_name);
        title.setText(data.getTitle()+"");

        ImageView btnMan = holder.getView(R.id.btnMan);

        if(0==data.getIsShow()){
            btnMan.setImageResource(R.mipmap.ic_me_sex_checknull);
        }else{
            btnMan.setImageResource(R.mipmap.ic_me_sex_check);
        }
    }

}
