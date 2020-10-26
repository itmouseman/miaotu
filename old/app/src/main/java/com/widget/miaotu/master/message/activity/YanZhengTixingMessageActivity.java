package com.widget.miaotu.master.message.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MsgVerifyBean;
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
public class YanZhengTixingMessageActivity extends MBaseActivity {

    @BindView(R.id.rcv_yanzhengtixing)
    RecyclerView rcv_yanzhengtixing;
    @BindView(R.id.btn_back)
    ImageButton buttonBack;
    @BindView(R.id.tv_title)
    TextView tv_title;


    private YanZhengTixingMessageAdapter adapter;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText("验证提醒");
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yanzhengtixing;
    }

    @Override
    protected void initDetailData() {

        rcv_yanzhengtixing.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YanZhengTixingMessageAdapter(this);
        rcv_yanzhengtixing.setAdapter(adapter);

        RetrofitFactory.getInstence().API().msgVerify(new HeadSysMessageBean("10", "1")).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<MsgVerifyBean>>(this) {
                    @Override
                    protected void onSuccess(BaseEntity<List<MsgVerifyBean>> t) throws Exception {
                        Logger.e(t.toString());
                        if (t.getStatus() == 100) {
                            if (t.getData().size() == 0) {
                                ToastUtils.showShort("数据为空");

                            } else {
                                adapter.addData(t.getData());

                            }
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                    }
                });
    }
}
