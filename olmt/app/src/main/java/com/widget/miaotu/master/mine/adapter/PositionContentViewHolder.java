package com.widget.miaotu.master.mine.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;

public class PositionContentViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_position_list;
    public PositionContentViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_position_list = itemView.findViewById(R.id.tv_position_list);
    }
}
