package com.widget.miaotu.master.message.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.R;

import com.widget.miaotu.base.BaseActivity;
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
public class SystemMessageActivity extends BaseActivity {

    @BindView(R.id.rv_sys_message)
    RecyclerView rvSysMessage;
    @BindView(R.id.srl_sys_msg)
    SmartRefreshLayout srlSysMsg;
    @BindView(R.id.btn_back)
    ImageButton buttonBack;
    @BindView(R.id.tv_title)
    TextView tv_title;


    private SystemMessageAdapter adapter;


    @Override
    protected void initView() {

        initDataH();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("系统消息");
    }

    @Override
    protected void initData() {
        rvSysMessage.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SystemMessageAdapter(this);
        rvSysMessage.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sys_message;
    }


    private void initDataH() {


        RetrofitFactory.getInstence().API().getSysMessage(new HeadSysMessageBean("10", "1")).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<SysMessageBean>>(SystemMessageActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SysMessageBean>> t) throws Exception {
                        if (t.getStatus() == 100) {
                            adapter.addData(t.getData());
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
}
