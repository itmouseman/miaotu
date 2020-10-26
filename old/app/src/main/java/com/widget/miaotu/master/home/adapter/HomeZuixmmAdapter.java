package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.widget.miaotu.http.bean.SListByTypeJavaBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeZuixmmAdapter extends RecyclerView.Adapter<HomeZuixmmAdapter.HomeZuixmmViewHolder> {


    private final Context mContext;
    private List<SListByTypeJavaBean> mSListByTypeJavaBeanLists = new ArrayList<>();
    private String mStringSpecName;

    public HomeZuixmmAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public HomeZuixmmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_zxmm_item, parent, false);
        return new HomeZuixmmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeZuixmmViewHolder holder, int position) {
        holder.tvHomeZxmm1.setText(mSListByTypeJavaBeanLists.get(position).getSeedlingName());

        holder.tvHomeZxmm3.setText("库存 " + mSListByTypeJavaBeanLists.get(position).getRepertory() + "");
        holder.tvHomeZxmm4.setText(mSListByTypeJavaBeanLists.get(position).getCompanyName() + " >");
        holder.tvHomeZxmm5.setText(mSListByTypeJavaBeanLists.get(position).getProvince() + " " + mSListByTypeJavaBeanLists.get(position).getCity());
        holder.tvHomeZxmm6.setText(mSListByTypeJavaBeanLists.get(position).getPrice() == 0 ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + mSListByTypeJavaBeanLists.get(position).getPrice() + "</big></big></font>/株")
        );


        if (TextUtils.isEmpty(mSListByTypeJavaBeanLists.get(position).getPlantType())) {
            holder.stvHomeZxmm.setVisibility(View.GONE);
        } else {
            holder.stvHomeZxmm.setVisibility(View.VISIBLE);
            holder.stvHomeZxmm.setText(mSListByTypeJavaBeanLists.get(position).getPlantType());
        }

        String oldJson = mSListByTypeJavaBeanLists.get(position).getSeedlingUrls();
        Gson gson = new Gson();
        List<ImgUrlJavaBean> imgUrlJavaBean = gson.fromJson(oldJson, new TypeToken<List<ImgUrlJavaBean>>() {
        }.getType());

        GlideUtils.loadUrl(mContext, imgUrlJavaBean.get(0).getT_url(), (ImageView) holder.ivHomeZxmm);


        List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(mSListByTypeJavaBeanLists.get(position).getSpec(), new TypeToken<List<HomeMmSpecJavaBean>>() {
        }.getType());

        if (homeMmSpecJavaBeanList.size() > 1) {
            mStringSpecName = homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                    + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit();
        } else if (homeMmSpecJavaBeanList.size() == 1) {
            mStringSpecName = homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit();
        }


        holder.tvHomeZxmm2.setText(mStringSpecName);

        if (mSListByTypeJavaBeanLists.get(position).getQuality() == 0) {//品质；0：清货；1：精品

            holder.ivHomeZxmmJP.setImageResource(R.mipmap.iv_home_qinghuo);
        } else if (mSListByTypeJavaBeanLists.get(position).getQuality() == 1) {
            holder.ivHomeZxmmJP.setImageResource(R.mipmap.iv_home_jinping);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] key = {SPConstant.TRANSTENT_CONTENT};
                String[] value = {mSListByTypeJavaBeanLists.get(position).getId() + ""};
                IntentUtils.startIntent(mContext, HomeTgAndMmActivity.class, key, value);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSListByTypeJavaBeanLists.size();
    }

    public void addData(List<SListByTypeJavaBean> sListByTypeJavaBeanLists) {
        mSListByTypeJavaBeanLists.addAll(sListByTypeJavaBeanLists);

        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearData() {
        this.mSListByTypeJavaBeanLists.clear();
//        notifyDataSetChanged();
    }

    public class HomeZuixmmViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvHomeZxmm1;
        private final TextView tvHomeZxmm2;
        private final TextView tvHomeZxmm3;
        private final TextView tvHomeZxmm4;
        private final TextView tvHomeZxmm5;
        private final TextView tvHomeZxmm6;
        private final ImageView ivHomeZxmm;
        private final ImageView ivHomeZxmmJP;
        private final ShapeTextView stvHomeZxmm;

        public HomeZuixmmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeZxmm1 = (TextView) itemView.findViewById(R.id.tv_home_zxmm1);
            tvHomeZxmm2 = (TextView) itemView.findViewById(R.id.tv_home_zxmm2);
            tvHomeZxmm3 = (TextView) itemView.findViewById(R.id.tv_home_zxmm3);
            tvHomeZxmm4 = (TextView) itemView.findViewById(R.id.tv_home_zxmm4);
            tvHomeZxmm5 = (TextView) itemView.findViewById(R.id.tv_home_zxmm5);
            tvHomeZxmm6 = (TextView) itemView.findViewById(R.id.tv_home_zxmm6);
            ivHomeZxmm = (ImageView) itemView.findViewById(R.id.iv_home_zxmm);
            ivHomeZxmmJP = (ImageView) itemView.findViewById(R.id.iv_home_zxmm_jp);
            stvHomeZxmm = (ShapeTextView) itemView.findViewById(R.id.stv_home_zxmm);


        }
    }

}
