package com.widget.miaotu.master.mine.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;

public class PositionHeadViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_me_index_info_head_title;
    public PositionHeadViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_me_index_info_head_title = itemView.findViewById(R.id.tv_me_index_info_head_title);
    }
}
