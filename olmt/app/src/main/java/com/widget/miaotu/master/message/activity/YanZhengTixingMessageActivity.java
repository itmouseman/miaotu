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
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MsgVerifyBean;
import com.widget.miaotu.http.bean.head.HeadHaoYouEdit;
import com.widget.miaotu.http.bean.head.HeadSysMessageBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.message.adapter.SystemMessageAdapter;
import com.widget.miaotu.master.message.adapter.YanZhengTixingMessageAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 验证提醒
 */
public class YanZhengTixingMessageActivity extends MBaseActivity implements YanZhengTixingMessageAdapter.ItemClickCallBack {

    @BindView(R.id.rcv_yanzhengtixing)
    RecyclerView rcv_yanzhengtixing;
    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.srfLayout_tiXing)
    SmartRefreshLayout smartRefreshLayout;


    private YanZhengTixingMessageAdapter adapter;
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

        mTopBar.setTitle("验证提醒").setTextColor(Color.parseColor("#EBF9FF"));

        rcv_yanzhengtixing.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YanZhengTixingMessageAdapter(this, this);
        rcv_yanzhengtixing.setAdapter(adapter);
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yanzhengtixing;
    }

    @Override
    protected void initDetailData() {


        getInitData();

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getInitData();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getInitData();
            }
        });
    }

    private void getInitData() {
        RetrofitFactory.getInstence().API().msgVerify(new HeadSysMessageBean(mPageNum, mPage)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<MsgVerifyBean>>(this) {
                    @Override
                    protected void onSuccess(BaseEntity<List<MsgVerifyBean>> t) throws Exception {
                        smartRefreshLayout.finishLoadMore(true);
                        if (t.getStatus() == 100) {

                            if (mPage == 1) {
                                adapter.setNewInstance(t.getData());
                                if (t.getData().size() < 10) {
                                    smartRefreshLayout.setNoMoreData(true);
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
                                smartRefreshLayout.setNoMoreData(true);
                                return;
                            }

                            for (MsgVerifyBean msgVerifyBean : t.getData()) {
                                adapter.addData(msgVerifyBean);
                            }
                            smartRefreshLayout.finishLoadMore(true);
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
    public void onTongGuoClick(MsgVerifyBean item) {
        //通过好友申请 friends/edit
        showWaiteDialog("加载中...");
        RetrofitFactory.getInstence().API().tongGuoHaoYou(new HeadHaoYouEdit(item.getId() + "", "2")).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(YanZhengTixingMessageActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort(t.getMessage());
                getInitData();
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort("请求异常");
            }
        });
    }

    @Override
    public void onHuLueClick(MsgVerifyBean item) {
        //忽略好友申请
        showWaiteDialog("加载中...");
        RetrofitFactory.getInstence().API().tongGuoHaoYou(new HeadHaoYouEdit(item.getId() + "", "3")).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(YanZhengTixingMessageActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort(t.getMessage());
                getInitData();
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort("请求异常");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(new HomeUpdateChange(false));
    }
}
