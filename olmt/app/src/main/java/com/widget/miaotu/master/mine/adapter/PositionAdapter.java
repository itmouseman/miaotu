package com.widget.miaotu.master.mine.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.PublicDicInfoBean;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter {

    //定义三种常量  表示三种条目类型
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_CONTENT = 1;

    public OnClickListener onClickListener;
    public interface OnClickListener{
        void onClick(int position, String title, int titleId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //实体类数据
    private List<PublicDicInfoBean.DataBean> mData;

    public PositionAdapter(List<PublicDicInfoBean.DataBean> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //创建不同的ViewHolder
        View view;

        //根据viewType来创建条目
        if (viewType == TYPE_HEAD) {
            view = View.inflate(viewGroup.getContext(), R.layout.item_me_index_head, null);
            return new PositionHeadViewHolder(view);
        } else {
            view = View.inflate(viewGroup.getContext(), R.layout.item_position_list, null);
            return new PositionContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof PositionHeadViewHolder) {
            PositionHeadViewHolder headViewHolder = (PositionHeadViewHolder) viewHolder;
            headViewHolder.tv_me_index_info_head_title.setText(mData.get(i).getJob_name());
        } else {
            PositionContentViewHolder contentViewHolder = (PositionContentViewHolder) viewHolder;
            contentViewHolder.tv_position_list.setText(mData.get(i).getJob_name());
            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(i,mData.get(i).getJob_name(),mData.get(i).getJob_id());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        PublicDicInfoBean.DataBean moreTypeBean = mData.get(position);
        if (moreTypeBean.getType() == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 处理在GridLayoutManager下单元格占据问题
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEAD
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
