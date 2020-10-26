package com.widget.miaotu.master.home.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.bean.GardenHeadlinesBean;
import com.widget.miaotu.master.home.activity.HotSpotInfomationActivity;
import com.widget.miaotu.master.home.adapter.GardenHeadlinesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : heyang
 * e-mail :
 * date   : 2020/8/179:38
 * desc   :园林IP
 * version: 1.0
 */
public class GardenIPFragment extends BaseFragment {

    @BindView(R.id.rcv_gardenHeadlines)
    RecyclerView rcvGardenHeadlines;

    @BindView(R.id.srf_garden_headlines)
    SmartRefreshLayout smartRefreshLayout;
    private final int programId;
    private GardenHeadlinesAdapter mAdapter;
    private int mPage = 0;

    public GardenIPFragment(int programId, Context context) {

        this.programId =  programId;
    }

    @Override
    protected void initViewAndData(View view) {
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHttpData();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage = mPage+1;
                getHttpData();
            }
        });

        rcvGardenHeadlines.setLayoutManager(new LinearLayoutManager(getFragmentContext()));
        mAdapter = new GardenHeadlinesAdapter(new ArrayList<>(), getActivity());
        rcvGardenHeadlines.setAdapter(mAdapter);


    }

    private void getHttpData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("page", String.valueOf(mPage))
                .add("num", "10")
                .add("programId", String.valueOf(programId))
                .build();
        Request request = new Request.Builder().url(ApiService.OLD_URl + "zmh/Information/selectInfoList").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                smartRefreshLayout.finishRefresh(true);
                Logger.i(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                smartRefreshLayout.finishRefresh(true);
                String resuylt = response.body().string();
                GardenHeadlinesBean gardenHeadlinesBean = JSON.parseObject(resuylt, GardenHeadlinesBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<GardenHeadlinesBean.ContentBean.ListBean> mList =  gardenHeadlinesBean.getContent().get(1).getList();

                        if (mPage == 0) {
                            mAdapter.setNewInstance(mList);
                            if (mList.size() < 10) {
                                smartRefreshLayout.setNoMoreData(true);
                            }

                            if (mList.size() == 0) {
                                View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
                                emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                mAdapter.setEmptyView(emptyView);
                            }
                            return;
                        }

                        if (mList.size() == 0) {
                            smartRefreshLayout.setNoMoreData(true);
                            return;
                        }

                        for (GardenHeadlinesBean.ContentBean.ListBean listBean : mList) {
                            mAdapter.addData(listBean);
                        }
                        smartRefreshLayout.finishLoadMore(true);
                    }
                });
            }
        });
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_gardenheadlines;
    }
}
