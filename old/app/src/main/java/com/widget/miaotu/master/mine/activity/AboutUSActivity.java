package com.widget.miaotu.master.mine.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;

import butterknife.BindView;

public class AboutUSActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageButton imageButton;
    @BindView(R.id.tv_title)
    TextView textView;
    @BindView(R.id.tv_us_version)
    TextView tv_us_version;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        textView.setText("关于苗途");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_us_version.setText(AppUtils.getAppVersionName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}
