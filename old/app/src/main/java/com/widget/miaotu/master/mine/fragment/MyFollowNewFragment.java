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

import com.blankj.utilcode.util.SPStaticUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
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
import com.widget.miaotu.http.bean.MyFollowListBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFollowNewFragment extends BaseFragment {


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



    String type;
    private CommonAdapter<MyFollowListBean> mAdapter;

    public MyFollowNewFragment(String userId, AppCompatActivity appCompatActivity) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
    }

    public MyFollowNewFragment(String userId, AppCompatActivity appCompatActivity, String type) {
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
            tvEmpty.setText("哎呀！您还没有关注企业哦，快去关注吧~");
        }
//        EventBus.getDefault().register(this);
        getDiscoverList(page, num);
    }




    private void getDiscoverList(int page, int num) {


        RetrofitFactory.getInstence().API().myFollowList(new IdAndPageBean(0, page, num))
                .compose(TransformerUi.<BaseEntity<List<MyFollowListBean>>>setThread())
                .subscribe(new BaseObserver<List<MyFollowListBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<MyFollowListBean>> t) throws Exception {

                        if (t.getStatus()==100){
                            empty.setVisibility(View.GONE);

//                            adapter = new FollowTreeListAdapter(new ArrayList<MyFollowListBean>(), appCompatActivity);
                            List<MyFollowListBean> listBeans =  t.getData();
                            mAdapter = new CommonAdapter<MyFollowListBean>(getFragmentContext(), R.layout.item_my_follow_list, listBeans) {
                                @Override
                                protected void convert(ViewHolder holder, MyFollowListBean item, int position) {
                                    if (!AndroidUtils.isNullOrEmpty(item.getLogo())) {
                                        Glide.with(mContext).load(item.getLogo()).into((ImageView) holder.getView(R.id.niv_demand_details_avatar));
                                    }

                                    holder.setText(R.id.tv_demand_details_nickname, item.getName()+"")
                                            .setText(R.id.tv_demand_details_position, item.getAddress()+"");

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String[] key = {SPConstant.COMPANY_ID, SPConstant.USER_ID};
                                            String[] value = {item.getId() + "",    "1"};

                                            IntentUtils.startIntent(mContext, CompanyDetailActivity.class, key, value);
                                        }
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
