package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class HomeSearchHistoryAdapter extends RecyclerView.Adapter<HomeSearchHistoryAdapter.HomeItemViewHodel> {


    private final CallbackJump mCallbackJump;
    private List<HomeSearchJavaBean> stringArrayList = new ArrayList<>();



    public HomeSearchHistoryAdapter( CallbackJump callbackJump) {

        mCallbackJump = callbackJump;
    }

    public void addData(List<HomeSearchJavaBean> homeSearchJavaBean){
        stringArrayList.addAll(homeSearchJavaBean);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public HomeItemViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_search_history_item, parent, false);

        return new HomeItemViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHodel holder, int position) {
        HomeSearchJavaBean homeSearchJavaBean = stringArrayList.get(position);
        holder.tvleft.setText(homeSearchJavaBean.getName());
        String[] mClassify = homeSearchJavaBean.getClassify().split("\\|");

        holder.setIsRecyclable(false);//禁止复用，防止item滑动重合

        if (mClassify.length == 1) {
            holder.tv1.setVisibility(VISIBLE);
            holder.tv1.setText(mClassify[0]);
        } else if (mClassify.length == 2) {
            holder.tv1.setVisibility(VISIBLE);
            holder.tv1.setText(mClassify[0]);
            holder.tv2.setVisibility(VISIBLE);
            holder.tv2.setText(mClassify[1]);
        } else {
            holder.tv1.setVisibility(VISIBLE);
            holder.tv1.setText(mClassify[0]);
            holder.tv2.setVisibility(VISIBLE);
            holder.tv2.setText(mClassify[1]);
            holder.tv3.setVisibility(VISIBLE);
            holder.tv3.setText(mClassify[2]);
        }
        if (mCallbackJump != null) {
            //跳转
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbackJump.jumpSearchDetail(homeSearchJavaBean);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public void clearData() {
        stringArrayList.clear();

    }


    public class HomeItemViewHodel extends RecyclerView.ViewHolder {

        TextView tvleft;
        TextView tv1;
        TextView tv2;
        TextView tv3;

        public HomeItemViewHodel(@NonNull View itemView) {
            super(itemView);
            tvleft = (TextView) itemView.findViewById(R.id.tv_search_history_item1);
            tv1 = (TextView) itemView.findViewById(R.id.tv_search_history_item2);
            tv2 = (TextView) itemView.findViewById(R.id.tv_search_history_item3);
            tv3 = (TextView) itemView.findViewById(R.id.tv_search_history_item4);

        }
    }


    public interface CallbackJump {

        void jumpSearchDetail(HomeSearchJavaBean homeSearchJavaBean);

    }
}
