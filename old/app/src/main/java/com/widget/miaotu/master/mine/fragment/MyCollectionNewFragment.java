package com.widget.miaotu.master.mine.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.IdAndPageBean;
import com.widget.miaotu.http.bean.MyCollectionListGetBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCollectionNewFragment extends BaseFragment {

    String userId;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_nursery)
    TextView tvAddNursery;
    @BindView(R.id.rl_recycle)
    RelativeLayout rlRecycle;
    @BindView(R.id.tv_add_nursery_2)
    TextView tvAddNursery2;
    @BindView(R.id.empty)
    RelativeLayout empty;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.image_all)
    ImageView imageAll;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;


    private AppCompatActivity appCompatActivity;

    private int page = 1;

    private int num = 10;
    private int gardenId = 0;

    private List<SeedListGetBean> mData = new ArrayList<>();

    String type;
    private CommonAdapter<MyCollectionListGetBean.CollectSeedlingListBean> mAdapter;


    public MyCollectionNewFragment(String userId, AppCompatActivity appCompatActivity) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
    }

    public MyCollectionNewFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }

    @Override
    protected void initViewAndData(View view) {
        if ("1".equals(type) || "3".equals(type)) {
            llAll.setVisibility(View.GONE);
            tvAddNursery.setVisibility(View.GONE);
        }
        if ("3".equals(type)) {
            tvEmpty.setText("哎呀！您还没有收藏苗木哦，快去收藏吧~");
        }
//        EventBus.getDefault().register(this);

        getDiscoverList(page, num);


    }


    private void getDiscoverList(int page, int num) {


        RetrofitFactory.getInstence().API().getCollectSeedList(new IdAndPageBean(0, page, num))
                .compose(TransformerUi.<BaseEntity<MyCollectionListGetBean>>setThread())
                .subscribe(new BaseObserver<MyCollectionListGetBean>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<MyCollectionListGetBean> t) throws Exception {
                        MyCollectionListGetBean myCollectionListGetBean = t.getData();
                        Logger.e(t.getData().toString());
                        if (t.getStatus() == 100) {
                            llAll.setVisibility(View.VISIBLE);
                            imageAll.setVisibility(View.GONE);
                            tvAll.setText("收藏苗木(" + myCollectionListGetBean.getTotal() + ")");
                            empty.setVisibility(View.GONE);


                            mAdapter = new CommonAdapter<MyCollectionListGetBean.CollectSeedlingListBean>(getFragmentContext(), R.layout.item_nursery_tree_list,
                                    myCollectionListGetBean.getCollectSeedlingList()) {
                                @Override
                                protected void convert(ViewHolder holder, MyCollectionListGetBean.CollectSeedlingListBean item, int position) {
                                    if (!AndroidUtils.isNullOrEmpty(item.getSeedlingUrls())) {
//                                        List<NewsInfoCoverBean> mData = JSON.parseArray(item.getSeedlingUrls(), NewsInfoCoverBean.class);
                                        Gson gson = new Gson();
                                        List<NewsInfoCoverBean> mData = gson.fromJson(item.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
                                        }.getType());
                                        if (mData != null && mData.size() > 0)
                                            Glide.with(mContext).load(mData.get(0).getT_url()).into((ImageView) holder.getView(R.id.niv_demand_list_avatar));
                                    }

                                    holder.setText(R.id.tv_demand_list_nickname, item.getSeedlingName() + "")
                                            .setText(R.id.tv_demand_list_nickname_type, item.getPlantType() + "")
                                            .setText(R.id.tv_demand_list_nick_stock, "库存 " + item.getRepertory() + "")
                                            .setText(R.id.tv_demand_list_address, item.getProvince() + " " + item.getCity() + "")
                                            .setText(R.id.tv_garden_name, item.getGardenName() + "");
                                    if (AndroidUtils.isNullOrEmpty(item.getGardenName())) {
//            helper.setVisible(R.id.ll_garden_name,false);
                                        holder.setText(R.id.tv_garden_name, item.getCompanyName() + "");
                                    } else {
                                        holder.setVisible(R.id.ll_garden_name, true);
                                    }
                                    if ("1".equals(item.getQuality())) {
                                        holder.setVisible(R.id.tv_quality, true);
                                        holder.setText(R.id.tv_quality, "精品");
                                        holder.getView(R.id.tv_quality).setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_demand_4));
                                    } else if ("0".equals(item.getQuality())) {
                                        holder.setVisible(R.id.tv_quality, true);
                                        holder.setText(R.id.tv_quality, "清货");
                                        holder.getView(R.id.tv_quality).setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_demand_4));
                                    } else {
                                        holder.setVisible(R.id.tv_quality, false);
                                    }
                                    if ("1".equals(item.getIsPromote())) {
                                        holder.setVisible(R.id.tv_promote, true);
                                    } else {
                                        holder.setVisible(R.id.tv_promote, false);
                                    }


                                    if ("0".equals(item.getStatus())) {
                                        holder.setVisible(R.id.tv_demand_review, true);
                                        holder.setVisible(R.id.imageView68, false);
                                    } else if ("3".equals(item.getStatus())) {
                                        holder.setVisible(R.id.tv_demand_review, false);
                                        holder.setVisible(R.id.imageView68, true);
                                    } else {
                                        holder.setVisible(R.id.tv_demand_review, false);
                                        holder.setVisible(R.id.imageView68, false);
                                    }
                                    if (AndroidUtils.isNullOrEmpty(item.getPrice()) || "0".equals(item.getPrice()) || "0.0".equals(item.getPrice()) || "0.00".equals(item.getPrice())) {
                                        holder.setText(R.id.tv_demand_list_nick_price, "面议");
                                    } else {
                                        holder.setText(R.id.tv_demand_list_nick_price, "￥" + item.getPrice());
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
                                                holder.setText(R.id.tv_demand_list_nick_high, specName + " " + interval + unit);
                                            }
                                            if (i == 1) {
                                                holder.setText(R.id.tv_demand_list_nick_crown, specName + " " + interval + unit);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    holder.itemView.setOnClickListener(v -> {
                                        Intent intent = new Intent(mContext, HomeTgAndMmActivity.class);
                                        intent.putExtra(SPConstant.ADDTREE_TYPE, "1");
                                        intent.putExtra(SPConstant.GARDEN_ID, item.getGardenId() + "");
                                        intent.putExtra(SPConstant.TRANSTENT_CONTENT, item.getId() + "");
                                        intent.putExtra(SPConstant.USER_ID, item.getUserId() + "");
                                        mContext.startActivity(intent);
                                    });
                                }
                            };


                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(mAdapter);


                        }

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());

                    }
                });


    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_nursery_tree;
    }
}
