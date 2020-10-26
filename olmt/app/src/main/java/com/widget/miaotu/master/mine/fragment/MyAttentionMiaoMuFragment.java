package com.widget.miaotu.master.mine.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.IdAndPageBean;
import com.widget.miaotu.http.bean.MyCollectionListGetBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的苗木收藏列表
 */
public class MyAttentionMiaoMuFragment extends BaseFragment {

    private final AppCompatActivity appCompatActivity;
    String userId;

    @BindView(R.id.recyclerView_my_collection_mm)
    RecyclerView recyclerView;
    @BindView(R.id.srf_my_collection_mm)
    SmartRefreshLayout smartRefreshLayout;


    private int page = 1;

    private int num = 10;


    String type;
    private BaseQuickAdapter mAdapter;


    public MyAttentionMiaoMuFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }


    @Override
    protected void initViewAndData(View view) {

        if ("1".equals(type) || "3".equals(type)) {


        }
        if ("3".equals(type)) {

        }
        initAdapter();

        getDiscoverList(page, num);

        smartRefreshLayout.setEnableLoadMore(true);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showWaiteDialog("正在加载中...");
                getDiscoverList(page, num);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getDiscoverList(page, num);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<MyCollectionListGetBean.CollectSeedlingListBean, BaseViewHolder>(R.layout.item_attention_miaomu, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder holder, MyCollectionListGetBean.CollectSeedlingListBean item) {

                Logger.i(item.toString());
                if (!AndroidUtils.isNullOrEmpty(item.getSeedlingUrls())) {
//                                        List<NewsInfoCoverBean> mData = JSON.parseArray(item.getSeedlingUrls(), NewsInfoCoverBean.class);
                    Gson gson = new Gson();
                    List<NewsInfoCoverBean> mData = gson.fromJson(item.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
                    }.getType());
                    if (mData != null && mData.size() > 0)
                        Glide.with(getFragmentContext()).load(mData.get(0).getT_url()).into((ImageView) holder.getView(R.id.rcv_attention_mm_head));
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getStatus()==4||item.getStatus()==0){
                            ToastUtils.showShort("已失效");
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("comFrom", "1");
                            intent.putExtra(SPConstant.TRANSTENT_CONTENT, item.getSeedlingId() + "");
                            intent.setClass(getFragmentContext(), HomeTgAndMmActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getFragmentContext().startActivity(intent);
                        }
                    }
                });

                holder.setText(R.id.tv_attention_mm_nickname, item.getSeedlingName() + "")

                        .setText(R.id.tv_attention_mm_stock, "库存 " + item.getRepertory() + "")
                        .setText(R.id.tv_attention_mm_address, item.getProvince() + " " + item.getCity() + "")
                        .setText(R.id.tv_attention_mm_garden_name, item.getGardenName() + "");

                if (!TextUtils.isEmpty(item.getPlantType())) {
                    holder.getView(R.id.tv_attention_mm_garden_nickname_type).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_attention_mm_garden_nickname_type, item.getPlantType() + "");
                }else {
                    holder.getView(R.id.tv_attention_mm_garden_nickname_type).setVisibility(View.GONE);
                }
                if (AndroidUtils.isNullOrEmpty(item.getGardenName())) {
//            helper.setVisible(R.id.ll_garden_name,false);
                    holder.setText(R.id.tv_attention_mm_garden_name, item.getCompanyName() + "");
                } else {
                    holder.setVisible(R.id.tv_attention_mm_garden_name, true);
                }
                if ("1".equals(item.getQuality())) {
                    holder.setVisible(R.id.rcv_attention_mm_quality, true);
                    holder.setText(R.id.rcv_attention_mm_quality, "精品");
                    holder.getView(R.id.rcv_attention_mm_quality).setBackground(getFragmentContext().getResources().getDrawable(R.drawable.bg_red_demand_4));
                } else if ("0".equals(item.getQuality())) {
                    holder.setVisible(R.id.rcv_attention_mm_quality, true);
                    holder.setText(R.id.rcv_attention_mm_quality, "清货");
                    holder.getView(R.id.rcv_attention_mm_quality).setBackground(getFragmentContext().getResources().getDrawable(R.drawable.bg_blue_demand_4));
                } else {
                    holder.setVisible(R.id.rcv_attention_mm_quality, false);
                }
//                if ("1".equals(item.getIsPromote())) {
//                    holder.setVisible(R.id.tv_promote, true);
//                } else {
//                    holder.setVisible(R.id.tv_promote, false);
//                }


                if (item.getStatus()==0) {//状态；0：审核中；1：审核通过；2：未通过;3：下架；4：删除状态

                    holder.setVisible(R.id.iv_attention_shixiao, true);
                } else if (item.getStatus()==3) {
                    holder.setVisible(R.id.iv_attention_shixiao, false);

                } else if (item.getStatus()==4){
                    holder.setVisible(R.id.iv_attention_shixiao, true);

                }
                if (AndroidUtils.isNullOrEmpty(item.getPrice()) || "0".equals(item.getPrice()) || "0.0".equals(item.getPrice()) || "0.00".equals(item.getPrice())) {
                    holder.setText(R.id.tv_attention_mm_price, "面议");
                } else {
                    holder.setText(R.id.tv_attention_mm_price, "￥" + item.getPrice());
                }
                //解析苗木属性json赋值 tv_demand_list_nick_high,tv_demand_list_nick_crown 冠
                try {
                    JSONArray jsonArray = new JSONArray(item.getSpec());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String specName = jsonObject.getString("specName");
                        String interval = jsonObject.getString("interval");
                        String unit = jsonObject.getString("unit");
                        if ("厘米".equals(unit)) {
                            unit = "cm";
                        } else if ("米".equals(unit)) {
                            unit = "m";
                        }
                        if (interval.contains("-")) {
                            if (interval.substring(0, interval.indexOf("-")).equals(interval.substring(interval.indexOf("-") + 1))) {
                                interval = interval.substring(0, interval.indexOf("-"));
                            }
                        }
                        if (i == 0) {
                            holder.setText(R.id.tv_attention_mm_high_and_guan, specName + " " + interval + unit);
                        }
                        if (i == 1) {
                            holder.setText(R.id.tv_attention_mm_high_and_guan, specName + " " + interval + unit);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

    }


    private void getDiscoverList(int page, int num) {

        RetrofitFactory.getInstence().API().getCollectSeedList(new IdAndPageBean(page, num))
                .compose(TransformerUi.<BaseEntity<MyCollectionListGetBean>>setThread())
                .subscribe(new BaseObserver<MyCollectionListGetBean>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<MyCollectionListGetBean> t) throws Exception {
                        hideWaiteDialog();
                        Logger.e("获取到的数据 " + t.toString());
                        MyCollectionListGetBean myCollectionListGetBean = t.getData();
                        smartRefreshLayout.finishRefresh(true);
                        if (t.getStatus() == 100) {
                            if (page == 1) {
                                mAdapter.setNewInstance(myCollectionListGetBean.getCollectSeedlingList());
                                if (myCollectionListGetBean.getCollectSeedlingList().size() < 10) {
                                    smartRefreshLayout.setNoMoreData(true);
                                }
                                if (myCollectionListGetBean.getCollectSeedlingList().size() == 0) {
                                    //空太显示
                                    View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
                                    //添加空视图
                                    mAdapter.setEmptyView(emptyView);
                                }

                                return;
                            }
                            if (myCollectionListGetBean.getCollectSeedlingList().size() == 0) {
                                smartRefreshLayout.setNoMoreData(true);
                                return;
                            }

                            for (MyCollectionListGetBean.CollectSeedlingListBean collectSeedlingListBean : myCollectionListGetBean.getCollectSeedlingList()) {
                                mAdapter.addData(collectSeedlingListBean);
                            }
                            smartRefreshLayout.finishLoadMore(true);

                        }

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                        hideWaiteDialog();
                    }
                });


    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_my_collection_mm;
    }
}
