package com.widget.miaotu.master.home.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.bean.HomeReMenHuoDongjavaBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ReMenHuodongFragment extends BaseFragment {


    @BindView(R.id.recyclerView_ReMenHuoDong)
    RecyclerView recyclerView_ReMenHuoDong;

    @BindView(R.id.reMen_refreshLayout)
    SmartRefreshLayout reMen_refreshLayout;


    private String mTitle;
    private CommonAdapter<HomeReMenHuoDongjavaBean.ContentBean> mAdapter;
    private int page = 1;
    private int mType = 10;

    public ReMenHuodongFragment(String mTitle) {
        this.mTitle = mTitle;
        if (mTitle.equals("全部")) {
            mType = 0;
        } else if (mTitle.equals("进行中")) {
            mType = 1;
        } else if (mTitle.equals("已结束")) {
            mType = 2;
        }
    }


    @Override
    protected void initViewAndData(View view) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getFragmentContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_ReMenHuoDong.setLayoutManager(layoutManager);


        getHttpData(mType, page, 10);

        refreshData();


    }

    private void refreshData() {

        reMen_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getHttpData(mType, page, 10);
                refreshLayout.finishRefresh(1000);
            }
        });
        //滑动监听
        reMen_refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < 100) {
                    page++;
                    Log.e("HYMypage", " -- - ---- " + page);

                    getHttpData(mType, page, 10);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    refreshLayout.setNoMoreData(false);
                }
                refreshLayout.finishLoadMore(1000);
            }
        });


    }

    /**
     * 服务器请求数据
     *
     * @param operatorType
     * @param page
     * @param num
     */
    private void getHttpData(int operatorType, int page, int num) {
        showWaiteDialog("正在加载...");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("operatorType", String.valueOf(operatorType))
                .add("page", String.valueOf(page))
                .add("num", String.valueOf(num))
                .build();
        Request request = new Request.Builder()
                .url(ApiService.HuoDongLieBIao)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("错误了");
                hideWaiteDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                hideWaiteDialog();

                Gson gson = new Gson();
                HomeReMenHuoDongjavaBean homeReMenHuoDongjavaBean = gson.fromJson(response.body().string(), HomeReMenHuoDongjavaBean.class);
                Logger.e(homeReMenHuoDongjavaBean.toString());

                if (homeReMenHuoDongjavaBean.getContent()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new CommonAdapter<HomeReMenHuoDongjavaBean.ContentBean>(getFragmentContext(), R.layout.home_remenhuodong_item, homeReMenHuoDongjavaBean.getContent()) {
                                @Override
                                protected void convert(ViewHolder holder, HomeReMenHuoDongjavaBean.ContentBean contentBean, int position) {
                                    ImageView imageView = holder.getView(R.id.iv_item_homeRM);
                                    GlideUtils.loadUrl(getFragmentContext(), contentBean.getCover(), imageView);
                                    switch (contentBean.getModel()) {
                                        case 4://活动
                                            holder.setImageResource(R.id.tv_item_homeRM_model, R.mipmap.iv_hdlb_hd);
                                            break;
                                        case 7://投票
                                            holder.setImageResource(R.id.tv_item_homeRM_model, R.mipmap.iv_hdlb_tp);
                                            break;
                                        case 8://众筹
                                            holder.setImageResource(R.id.tv_item_homeRM_model, R.mipmap.iv_hdlb_zc);
                                            break;
                                    }

                                    holder.setText(R.id.tv_item_homeRm_title, "\t\t\t\t\t\t" + contentBean.getTitle());
                                    holder.setText(R.id.tv_item_homeRm_time, contentBean.getStartTime() + "-" + contentBean.getEndTime());
                                    holder.setText(R.id.tv_item_homeRM_money, "￥" + contentBean.getPrice());
                                    ShapeTextView shapeTextView = holder.getView(R.id.sv_item_homeRm_ljBm);
                                    shapeTextView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ToastUtils.showShort("立即报名");

                                        }
                                    });
                                }
                            };
                            recyclerView_ReMenHuoDong.setAdapter(mAdapter);


//                        if (page == 1) {
//                            reMen_refreshLayout.setNoMoreData(false);
//                            mAdapter.clearData();
//                        }
//                        if (homeReMenHuoDongjavaBean.getContent().size() > 0) {
//                            mAdapter.
////                                homeZuixmmAdapter.notifyDataSetChanged();
//                        }

                        }
                    });
                }


            }
        });
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_re_men_huodong;
    }
}