package com.widget.miaotu.master.mine.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.widget.miaotu.http.bean.MyFollowListBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * 我的关注企业
 */
public class MyAttentionCompanyFragment extends BaseFragment {


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
    @BindView(R.id.ll_promote)
    LinearLayout ll_promote;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;


    private AppCompatActivity appCompatActivity;

    private int page = 1;

    private int num = 10;
    private int gardenId = 0;


    String type;
    private CommonAdapter<MyFollowListBean> mAdapter;


    public MyAttentionCompanyFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }

    @Override
    protected void initViewAndData(View view) {
        ll_promote.setVisibility(View.GONE);
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


        RetrofitFactory.getInstence().API().myFollowList(new IdAndPageBean(Integer.parseInt(userId), page, num))
                .compose(TransformerUi.<BaseEntity<List<MyFollowListBean>>>setThread())
                .subscribe(new BaseObserver<List<MyFollowListBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<MyFollowListBean>> t) throws Exception {
                        Logger.e(t.toString());
                        if (t.getStatus() == 100) {

                            if (t.getData().size() == 0) {
                                empty.setVisibility(View.VISIBLE);
                            } else {
                                empty.setVisibility(View.GONE);
                            }
                            List<MyFollowListBean> listBeans = t.getData();
                            mAdapter = new CommonAdapter<MyFollowListBean>(getFragmentContext(), R.layout.item_my_follow_list, listBeans) {
                                @Override
                                protected void convert(ViewHolder holder, MyFollowListBean item, int position) {
                                    if (item != null) {
                                        if (!AndroidUtils.isNullOrEmpty(item.getLogo())) {
                                            Glide.with(mContext).load(item.getLogo()).into((ImageView) holder.getView(R.id.niv_demand_details_avatar));
                                        }

                                        holder.setText(R.id.tv_demand_details_nickname, item.getName() + "")
                                                .setText(R.id.tv_demand_details_position, item.getAddress() + "");

                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String[] key = {SPConstant.COMPANY_ID, SPConstant.USER_ID};
                                                String[] value = {item.getId() + "", "1"};

                                                IntentUtils.startIntent(mContext, CompanyDetailActivity.class, key, value);
                                            }
                                        });
                                    }
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
