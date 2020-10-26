package com.widget.miaotu.master.other;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 用户服务协议
 */
public class UserServiceInfoActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        StatusBarUtil.fullScreen(this);
        tv_title.setText("用户服务协议");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_service_info;
    }
}