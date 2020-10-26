package com.widget.miaotu.master.mine.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.rxbus.MiaoMuDataChage;
import com.widget.miaotu.common.utils.rxbus.MyLocation;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;
import com.widget.miaotu.master.mine.adapter.GongYingMiaoMuAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的页面进入 ->  供应苗木
 */
public class GongYingMiaoMuFragment extends BaseFragment {


    @BindView(R.id.recyclerView_GongYingMm_Fg)
    RecyclerView recyclerView;
    @BindView(R.id.srfl_gongyingmiaomu)
    SmartRefreshLayout smartRefreshLayout;


    private final AppCompatActivity appCompatActivity;
    String type;
    String userId;
    private GongYingMiaoMuAdapter adapter;
    private int mPage = 1;
    private int mPageNum = 10;


    public GongYingMiaoMuFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }


    @Override
    protected void initViewAndData(View view) {
        adapter = new GongYingMiaoMuAdapter(getFragmentContext(), R.layout.item_gong_ying_miao_mu_list, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        RxBus.getInstance().toObservableSticky(this, MiaoMuDataChage.class).subscribe(new Consumer<MiaoMuDataChage>() {
            @Override
            public void accept(MiaoMuDataChage miaoMuDataChage) throws Exception {

                if (miaoMuDataChage.isChange()) {
                    mPage = 1;
                    getGardenList(mPage, mPageNum);
                }
            }
        });

        getGardenList(mPage, mPageNum);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                getGardenList(mPage, mPageNum);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getGardenList(mPage, mPageNum);
            }
        });
    }


    private void getGardenList(int page, int num) {

        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(page, num))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {

                        Logger.e(t.getData().toString());
                        smartRefreshLayout.finishRefresh(true);
                        smartRefreshLayout.finishLoadMore(true);
                        if (mPage == 1) {
                            adapter.setNewData(t.getData());
                            if (t.getData().size() < 10) {
                                smartRefreshLayout.setNoMoreData(true);
                            }

                            if (t.getData().size() == 0) {

                                View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                                emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                //添加空视图
                                adapter.setEmptyView(emptyView);
                            }
                            return;
                        }
                        if (t.getData().size() == 0) {
                            smartRefreshLayout.setNoMoreData(true);
                            return;
                        }

                        for (SeedListGetBean seedListGetBean : t.getData()) {
                            adapter.addData(seedListGetBean);
                        }
                        smartRefreshLayout.finishLoadMore(true);

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());

                    }
                });
    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_gongying_miaomu;
    }
}
