package com.widget.miaotu.master.home.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.http.bean.HomeFenLeiJavaBean;
import com.widget.miaotu.master.mvp.HomeFenLeiConrrol;
import com.widget.miaotu.master.mvp.HomeFenLeiView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFenLeiActivity extends MBaseActivity<HomeFenLeiConrrol> implements HomeFenLeiView, OnRefreshListener {

    @BindView(R.id.home_fenLei_recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.home_fenLei_refreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected HomeFenLeiConrrol createControl() {
        return new HomeFenLeiConrrol();
    }

    @Override
    protected void initView() {

        QMUIStatusBarHelper.translucent(this);
        tvTitle.setText("分类");
        smartRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_fen_lei;
    }

    @Override
    protected void initDetailData() {
        showWaiteDialog("正在加载中...");
        mControl.getData();
    }

    @OnClick({R.id.btn_back})
    public void onClicked(View view) {
        finish();
    }

    /**
     * 数据结果请求成功
     *
     * @param homeFenLeiJavaBean
     */
    @Override
    public void getDataSuc(List<HomeFenLeiJavaBean> homeFenLeiJavaBean) {
        smartRefreshLayout.finishRefresh(true);
        hideWaiteDialog();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        CommonAdapter mAdapter = new CommonAdapter<HomeFenLeiJavaBean>(this, R.layout.home_fenlei_item, homeFenLeiJavaBean) {
            @Override
            protected void convert(ViewHolder holder, HomeFenLeiJavaBean homeFenLeiJavaBean, int position) {

                holder.setText(R.id.tv_home_fenLei_item, homeFenLeiJavaBean.getName());
                GlideUtils.loadUrl(HomeFenLeiActivity.this, homeFenLeiJavaBean.getImg(), holder.getView(R.id.iv_home_fenLei_item));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //跳转到搜索详情页面
                        String[] key = {SPConstant.SEARCH_INFO};
                        String[] value = {homeFenLeiJavaBean.getName()};
                        IntentUtils.startIntent(HomeFenLeiActivity.this, HomeSearchDetailActivity.class, key, value);
                    }
                });
            }
        };

        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void getDataFail(String message) {
        smartRefreshLayout.finishRefresh(true);
        hideWaiteDialog();
        ToastUtils.showShort(message);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showWaiteDialog("正在加载中...");
        mControl.getData();

    }
}