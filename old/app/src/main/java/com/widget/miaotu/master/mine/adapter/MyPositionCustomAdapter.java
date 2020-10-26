package com.widget.miaotu.master.mine.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;


import java.util.List;

public class MyPositionCustomAdapter extends RecyclerView.Adapter<MyPositionCustomAdapter.MyPositionCustomViewHolder> {


    private final List<String> listData;
    private final Context context;

    public MyPositionCustomAdapter(Context context, List<String> listData) {
        this.listData = listData;
        this.context = context;
    }


    @NonNull
    @Override
    public MyPositionCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_list, parent, false);
        return new MyPositionCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPositionCustomViewHolder holder, int position) {
        holder.mTitle.setText(listData.get(position));

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除
                String custom = SPStaticUtils.getString(SPConstant.POSITION_CUSTOM);
                if (!AndroidUtils.isNullOrEmpty(custom)) {

                    custom = custom.replaceAll(listData.get(position) + ",", "");

                    SPStaticUtils.put(SPConstant.POSITION_CUSTOM, custom);
                }
                listData.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyPositionCustomViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;
        private final ImageView mImage;

        public MyPositionCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_setting_title);
            mImage = itemView.findViewById(R.id.image_setting_title);
        }
    }
}
