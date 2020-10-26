package com.widget.miaotu.master.miaopu.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;
import com.widget.miaotu.master.miaopu.adapter.ManagerQiuGouMiaoMuAdapater;

import java.util.List;

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
    private int page = 1;
    private int num = 10;

    public ManagerGyinmiaomuListFragment(String userId, ManagerNurseryActivity managerNurseryActivity, String s1) {
        mUserId = userId;
    }


    @Override
    protected void initViewAndData(View view) {
        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(page, num, mUserId))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {
                        hideWaiteDialog();

                        Logger.e(t.getData().toString());

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new ManagerQiuGouMiaoMuAdapater(R.layout.item_mannager_qiuqou,t.getData()));
                        recyclerView.setNestedScrollingEnabled(false);


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
