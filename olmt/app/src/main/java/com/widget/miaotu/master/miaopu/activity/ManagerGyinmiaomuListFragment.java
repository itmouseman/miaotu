package com.widget.miaotu.master.miaopu.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.dialog.AddTreeDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.ChangePassWordJavaBean;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.head.HeadStatusBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.home.activity.BaseInfoDemandTechAddActivity;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;
import com.widget.miaotu.master.miaopu.adapter.ManagerQiuGouMiaoMuAdapater;
import com.widget.miaotu.master.mine.activity.ChangePasswordActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * 管理  供应苗木列表
 */
public class ManagerGyinmiaomuListFragment extends BaseFragment {
    private final String mUserId;
    @BindView(R.id.recyclerView_only)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_srf_only)
    SmartRefreshLayout swipeRefreshLayout;

    @BindView(R.id.ll_add_miao_mu_bottom)
    LinearLayout linearLayout_bottom;
    @BindView(R.id.stv_add_miao_mu_cance)
    ShapeTextView cance;
    @BindView(R.id.stv_add_miao_mu_nursery)
    ShapeTextView addMiaoMu;
    private int mPage = 1;
    private int num = 10;
    private List<SeedListGetBean> mData;
    private ManagerQiuGouMiaoMuAdapater mAdapter;

    public ManagerGyinmiaomuListFragment(String userId, ManagerNurseryActivity managerNurseryActivity, String s1) {
        mUserId = userId;
    }


    @Override
    protected void initViewAndData(View view) {
        //列表刷新
        swipeRefreshLayout.setEnableRefresh(true);
        swipeRefreshLayout.setEnableLoadMore(true);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getListData();
            }
        });
        swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getListData();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ManagerQiuGouMiaoMuAdapater(getFragmentContext(),R.layout.item_mannager_qiuqou, new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        cance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_bottom.setVisibility(View.GONE);
            }
        });
        addMiaoMu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否有苗圃 分别进入苗圃页面或添加苗木页面
                List mdata = new ArrayList<NurseryNameBean>();
                RetrofitFactory.getInstence().API().gardenList().compose(TransformerUi.setThread())
                        .subscribe(new BaseObserver<List<GardenListBean>>(getFragmentContext()) {
                            @Override
                            protected void onSuccess(BaseEntity<List<GardenListBean>> t) throws Exception {

                                Logger.e(t.toString());
                                if (t.getStatus() == 100) {
                                    List<GardenListBean> gardenListBeans = t.getData();

                                    if (gardenListBeans != null) {
                                        for (GardenListBean graden : gardenListBeans) {
                                            mdata.add(new NurseryNameBean(graden.getGardenName(), graden.getId() + "", 0));
                                        }

                                        if (mdata.size() == 0) {//没有可添加苗木的苗圃，请先添加苗圃
                                            startActivity(new Intent(getFragmentContext(), BaseInfoDemandTechAddActivity.class));

                                            return;
                                        }

                                        if (mdata.size() == 1) {//跳转到添加苗木界面
                                            Intent intent = new Intent();
                                            intent.putExtra("type", "0");
                                            intent.putExtra("gardenId", String.valueOf(gardenListBeans.get(0).getId()));

                                            intent.setClass(getFragmentContext(), AddMiaoMuActivity.class);
                                            startActivity(intent);

                                            return;
                                        }
                                        new AddTreeDialog(getContext(), mdata).show();

                                    }
                                } else {
                                    ToastUtils.showShort(t.getMessage());
                                }
                            }

                            @Override
                            protected void onFail(Throwable throwable) throws Exception {
                                ToastUtils.showShort("网络错误");
                            }
                        });


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListData();
    }

    private void getListData() {
//        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(mPage, num, mUserId))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {
                        hideWaiteDialog();
                        mData = t.getData();
                        if (t.getStatus() == 100) {
                            if (mPage == 1) {
                                mAdapter.setNewData(mData);
                                if (mData.size() < 10) {
                                    swipeRefreshLayout.setNoMoreData(true);
                                }

                                if (mData.size() == 0) {
                                    //数据为空
                                    View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
                                    //添加空视图
                                    mAdapter.setEmptyView(emptyView);
                                    linearLayout_bottom.setVisibility(View.VISIBLE);
                                }
                                return;
                            }

                            if (mData.size() == 0) {
                                swipeRefreshLayout.setNoMoreData(true);
                                return;
                            }

                            for (SeedListGetBean seedListGetBean : mData) {
                                mAdapter.addData(seedListGetBean);
                            }
                        } else {
                            ToastUtils.showShort(t.getMessage());
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
        return R.layout.layout_only_recycleview;
    }



}
