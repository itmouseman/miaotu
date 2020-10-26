package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.SeedlingInfo;

import java.util.List;


/**
 * 分组列表适配器
 * Create by: chenWei.li
 * Date: 2018/11/3
 * Time: 下午8:19
 * Email: lichenwei.me@foxmail.com
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<SeedlingInfo> mList;

    public RecyclerViewAdapter(Context context, List<SeedlingInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewAdapter.ViewHolder viewHolder = null;
        if (viewHolder == null) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_text, parent, false);
            viewHolder = new RecyclerViewAdapter.ViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        String text = mList.get(position).getBaseName();
        holder.mTextView.setText(text);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    /**
     * 判断position对应的Item是否是组的第一项
     *
     * @param position
     * @return
     */
    public boolean isItemHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            String lastGroupName = mList.get(position - 1).getBeginLetter();
            String currentGroupName = mList.get(position).getBeginLetter();
            //判断上一个数据的组别和下一个数据的组别是否一致，如果不一致则是不同组，也就是为第一项（头部）
            if (lastGroupName.equals(currentGroupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 获取position对应的Item组名
     *
     * @param position
     * @return
     */
    public String getGroupName(int position) {
        return mList.get(position).getBeginLetter();
    }


    public interface OnClickListener{
        void onClick(SeedlingInfo bean);
    }

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 自定义ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_text);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(mList.get(getAdapterPosition()));
                }
            });
        }
    }

}
