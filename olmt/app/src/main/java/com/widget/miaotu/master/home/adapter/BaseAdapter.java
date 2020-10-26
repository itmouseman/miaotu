package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杭州马后科技有限公司
 */

public abstract class BaseAdapter<T, B extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected List<T> listData;
    protected int layoutId;

    private int headViewId;
    private int bottomViewId;

    private boolean isHaveHead;
    private boolean isHaveBottom;

    private final int HEAD = 0;
    private final int NORMAL = 1;
    private final int BOTTOM = 2;

    private OnBindHeadListener onBindHeadListener;
    private OnBindBottomListener onBindBottomListener;
    private OnItemClickListener onItemClickListener;

    public BaseAdapter(@NonNull Context mContext, @NonNull List<T> listData,
                       @LayoutRes int layoutId) {
        this.mContext = mContext;
        this.listData = listData;
        this.layoutId = layoutId;
    }

    public void addHeadViewId(@LayoutRes int headViewId) {
        isHaveHead = true;
        this.headViewId = headViewId;
        notifyDataSetChanged();
    }

    public void addBottomViewId(@LayoutRes int bottomViewId) {
        isHaveBottom = true;
        this.bottomViewId = bottomViewId;
        notifyDataSetChanged();
    }

    public void removeHeadView() {
        if (isHaveHead) {
            isHaveHead = false;
            headViewId = 0;
            notifyItemRemoved(0);
        }
    }

    public void removeBottomView() {
        if (isHaveBottom) {
            bottomViewId = 0;
            notifyItemRemoved(getItemCount() - 1);
            isHaveBottom = false;
        }
    }

    /**
     * 是否含头部.
     *
     * @return
     */
    public boolean isHaveHead() {
        return isHaveHead;
    }

    /**
     * 是否含底部.
     *
     * @return
     */
    public boolean isHaveBottom() {
        return isHaveBottom;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;

        switch (viewType) {
            case HEAD:
                viewHolder = new BaseViewHolder(LayoutInflater.from(mContext)
                        .inflate(headViewId, parent, false));
                break;
            case NORMAL:
                viewHolder = new BaseViewHolder(LayoutInflater.from(mContext)
                        .inflate(layoutId, parent, false));
                break;
            case BOTTOM:
                Log.d("BaseAdapter", "viewType:" + viewType);

                viewHolder = new BaseViewHolder(LayoutInflater.from(mContext)
                        .inflate(bottomViewId, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHaveHead && isHead(position)) {
            onBindHead(holder);
        } else {
            onBindViewHolderNext(holder, position);
        }
    }

    private void onBindViewHolderNext(BaseViewHolder holder, int position) {
        if (isHaveBottom && isBottom(position)) {
            onBindBottom(holder);
        } else {
            int mPosition = isHaveHead ? position - 1 : position;
            onBindViewHolder(holder, listData.get(mPosition), mPosition);
            initItemListener(holder, isHaveHead ? position - 1 : position);
        }
    }

    private void initItemListener(BaseViewHolder holder, final int position) {
        if (null == onItemClickListener) return;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isHaveHead && isHaveBottom)
            return listData.size() + 2;

        if (isHaveHead || isHaveBottom)
            return listData.size() + 1;

        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHaveHead && isHaveBottom)
            if (isHead(position)) {
                return HEAD;
            } else if (isBottom(position)) {
                return BOTTOM;
            } else {
                return NORMAL;
            }

        if (isHaveHead)
            return isHead(position) ? HEAD : NORMAL;

        if (isHaveBottom)
            return isBottom(position) ? BOTTOM : NORMAL;

        return NORMAL;
    }

    private boolean isHead(int position) {
        return position == 0;
    }

    private boolean isBottom(int position) {
        return isHaveHead ? position == listData.size() + 1 : position == listData.size();
    }

    public abstract void onBindViewHolder(BaseViewHolder holder, T data, int position);

    public void onBindHead(BaseViewHolder holder) {
        if (null != onBindHeadListener) onBindHeadListener.onBindHead(holder);
    }

    public void onBindBottom(BaseViewHolder holder) {
        if (null != onBindBottomListener) onBindBottomListener.onBindBottom(holder);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnBindHeadListener(@NonNull OnBindHeadListener onBindHeadListener) {
        this.onBindHeadListener = onBindHeadListener;
    }

    public void setOnBindBottomListener(@NonNull OnBindBottomListener onBindBottomListener) {
        this.onBindBottomListener = onBindBottomListener;
    }

    public interface OnBindHeadListener {
        public void onBindHead(BaseViewHolder holder);
    }

    public interface OnBindBottomListener {
        public void onBindBottom(BaseViewHolder holder);
    }

    public interface OnItemClickListener {
        public void onClick(int position);
    }

    /**
     * 返回绑定的数据的克隆.
     *
     * @return
     */
    public List<T> getListData() {
        return (List<T>) ((ArrayList) listData).clone();
    }


    public void addData(List<T> data) {
        for (int i = 0; i < data.size(); i++) {
            listData.add((T) data);
        }
    }

    public void removeAddPhoto() {
        listData.remove(listData.size() - 1);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    public void addDataFirstPhoto(T data) {
        listData.add(0, data);
        notifyDataSetChanged();
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
                    return getItemViewType(position) == HEAD
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}

