package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchDetailAdapter extends RecyclerView.Adapter<HomeSearchDetailAdapter.HomeSearchDetailViewHoldel> {


    private Context mContext;

    private List<HomeSearchDetailJavaBean.ClassifyCountInfoBean> classifyCountInfo = new ArrayList<>();
    private List<HomeSearchDetailJavaBean.SeedlingBaseInfosBean> seedlingBaseInfos = new ArrayList<>();
    private List<HomeSearchDetailJavaBean.CompanyBaseInfosBean> companyBaseInfos = new ArrayList<>();

    @NonNull
    @Override
    public HomeSearchDetailViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_search_detail_item, parent, false);

        return new HomeSearchDetailViewHoldel(view);
    }

    @Override
    public int getItemCount() {
        return seedlingBaseInfos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSearchDetailViewHoldel holder, int position) {
        HomeSearchDetailJavaBean.SeedlingBaseInfosBean seedlingBaseInfosBean = seedlingBaseInfos.get(position);//[{"city":"徐州市","companyId":5738,"companyName":"张磊15051876396的企业","createTime":"","distance":506,"gardenId":null,"gardenName":"","id":3455,"isForceOn":0,"isPromote":0,"isVip":1,"lat":34.205768,"lon":117.284124,"moreSeedling":1,"plantType":"容器苗","price":"0","province":"江苏省","quality":2,"reason":"","repertory":100,"secondClassify":"单杆","seedlingId":4817,"seedlingName":"美国红枫","seedlingUrls":"","status":1,"userId":22782}]

        String imgUrls = seedlingBaseInfosBean.getSeedlingUrls();
        if (!TextUtils.isEmpty(imgUrls)) {
            Gson gson = new Gson();
            List<ImgUrlJavaBean> imgUrlJavaBeanList = gson.fromJson(imgUrls, new TypeToken<List<ImgUrlJavaBean>>() {
            }.getType());
            GlideUtils.loadUrl(mContext, imgUrlJavaBeanList.get(0).getT_url(), holder.ivLeft);
        }

        holder.tvDetail1.setText(seedlingBaseInfosBean.getSeedlingName());
        if (!TextUtils.isEmpty(seedlingBaseInfosBean.getSecondClassify())){
            holder.tvDetail2.setText(seedlingBaseInfosBean.getSecondClassify());
        }else {
            holder.tvDetail2.setVisibility(View.GONE);
        }

        Gson gson = new Gson();
        String m = seedlingBaseInfosBean.getSpec().toString().replace("\'", "\"");
        List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(m, new TypeToken<List<HomeMmSpecJavaBean>>() {
        }.getType());
        if (homeMmSpecJavaBeanList.size() > 1) {
            holder.tvDetail3.setText(   homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                    + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit());

        } else if (homeMmSpecJavaBeanList.size() == 1) {
            holder.tvDetail3.setText( homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit());

        }


        holder.tvDetail4.setText("库存" + seedlingBaseInfosBean.getRepertory());
        if (seedlingBaseInfosBean.getIsVip()==1){
            holder.tvDetail5.setVisibility(View.VISIBLE);
        }else {
            holder.tvDetail5.setVisibility(View.GONE);
        }
        if (seedlingBaseInfosBean.getNameAuth()==1){
            holder.tvDetail6.setVisibility(View.VISIBLE);
        }else {
            holder.tvDetail6.setVisibility(View.GONE);
        }



        holder.tvDetail7.setText(seedlingBaseInfosBean.getCompanyName());
        holder.tvDetail8.setText(seedlingBaseInfosBean.getProvince() + seedlingBaseInfosBean.getCity() + "|" + seedlingBaseInfosBean.getDistance() / 100 + "km");

        holder.tvDetail9.setText(seedlingBaseInfosBean.getPrice() == 0 ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedlingBaseInfosBean.getPrice() + "</big></big></font>/株"));


        holder.tvDetail10.setText(" ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] key = {SPConstant.ADDTREE_TYPE, SPConstant.GARDEN_ID, SPConstant.TRANSTENT_CONTENT, SPConstant.USER_ID};
                String[] value = {"1", seedlingBaseInfosBean.getGardenId() + "", seedlingBaseInfosBean.getId() + "", seedlingBaseInfosBean.getUserId() + ""};

                IntentUtils.startIntent(mContext, HomeTgAndMmActivity.class, key, value);
            }
        });

    }

    public void addData(Context context, HomeSearchDetailJavaBean homeSearchDetailJavaBean) {
        mContext = context;
        if (homeSearchDetailJavaBean.getSeedlingBaseInfos() != null) {
            seedlingBaseInfos.addAll(homeSearchDetailJavaBean.getSeedlingBaseInfos());
        }
        if (homeSearchDetailJavaBean.getClassifyCountInfo() != null) {
            classifyCountInfo.addAll(homeSearchDetailJavaBean.getClassifyCountInfo());

        }
        if (homeSearchDetailJavaBean.getCompanyBaseInfos() != null) {
            companyBaseInfos.addAll(homeSearchDetailJavaBean.getCompanyBaseInfos());

        }
        notifyDataSetChanged();
    }

    public void clearData() {
        seedlingBaseInfos.clear();
        classifyCountInfo.clear();
        companyBaseInfos.clear();

    }

    class HomeSearchDetailViewHoldel extends RecyclerView.ViewHolder {

        private final ImageView ivLeft;
        private final TextView tvDetail1;
        private final ShapeTextView tvDetail2;
        private final TextView tvDetail3;
        private final TextView tvDetail4;
        private final ShapeTextView tvDetail5;
        private final ShapeTextView tvDetail6;
        private final TextView tvDetail7;
        private final TextView tvDetail8;
        private final TextView tvDetail9;
        private final TextView tvDetail10;

        public HomeSearchDetailViewHoldel(@NonNull View itemView) {
            super(itemView);
            ivLeft = itemView.findViewById(R.id.iv_home_search_detail_item);
            tvDetail1 = itemView.findViewById(R.id.tv_home_search_detail_item1);
            tvDetail2 = itemView.findViewById(R.id.tv_home_search_detail_item2);
            tvDetail3 = itemView.findViewById(R.id.tv_home_search_detail_item3);
            tvDetail4 = itemView.findViewById(R.id.tv_home_search_detail_item4);
            tvDetail5 = itemView.findViewById(R.id.tv_home_search_detail_item5);
            tvDetail6 = itemView.findViewById(R.id.tv_home_search_detail_item6);
            tvDetail7 = itemView.findViewById(R.id.tv_home_search_detail_item7);
            tvDetail8 = itemView.findViewById(R.id.tv_home_search_detail_item8);
            tvDetail9 = itemView.findViewById(R.id.tv_home_search_detail_item9);
            tvDetail10 = itemView.findViewById(R.id.tv_home_search_detail_item10);
        }
    }

}
