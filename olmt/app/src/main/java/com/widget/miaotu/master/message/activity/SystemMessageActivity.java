package com.widget.miaotu.master.message.activity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;

import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SysMessageBean;
import com.widget.miaotu.http.bean.head.HeadSysMessageBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.message.adapter.SystemMessageAdapter;


import java.util.List;

import butterknife.BindView;

/**
 * 系统消息列表
 */
public class SystemMessageActivity extends MBaseActivity {

    @BindView(R.id.rv_sys_message)
    RecyclerView rvSysMessage;
    @BindView(R.id.srl_sys_msg)
    SmartRefreshLayout srlSysMsg;

    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;


    private SystemMessageAdapter adapter;

    private int mPageNum = 10;
    private int mPage = 1;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("系统消息").setTextColor(Color.parseColor("#EBF9FF"));


        rvSysMessage.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SystemMessageAdapter(this);
        rvSysMessage.setAdapter(adapter);
    }


    @Override
    protected void initDetailData() {

        initDataH();
        srlSysMsg.setEnableRefresh(true);
        srlSysMsg.setEnableLoadMore(true);
        srlSysMsg.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initDataH();
            }
        });
        srlSysMsg.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                initDataH();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sys_message;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    private void initDataH() {

        RetrofitFactory.getInstence().API().getSysMessage(new HeadSysMessageBean(mPageNum, mPage)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<SysMessageBean>>(SystemMessageActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SysMessageBean>> t) throws Exception {

                        srlSysMsg.finishRefresh(true);

                        if (t.getStatus() == 100) {
                            if (mPage == 1) {
                                adapter.setNewInstance(t.getData());
                                if (t.getData().size() < 10) {
                                    srlSysMsg.finishLoadMore();
                                    srlSysMsg.setNoMoreData(true);
                                    srlSysMsg.resetNoMoreData();
                                }
                                if (t.getData().size() == 0) {
                                    View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
                                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
                                    adapter.setEmptyView(emptyView);
                                }
                                return;
                            }

                            if (t.getData().size() == 0) {
                                srlSysMsg.setNoMoreData(true);
                                return;
                            }

                            for (SysMessageBean sysMessageBean : t.getData()) {
                                adapter.addData(sysMessageBean);
                            }
                            srlSysMsg.finishLoadMore(true);
                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                        ToastUtils.showShort("网络错误");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(new HomeUpdateChange(false));
    }
}
