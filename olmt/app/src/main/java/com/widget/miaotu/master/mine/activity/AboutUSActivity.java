package com.widget.miaotu.master.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;

import butterknife.BindView;

public class AboutUSActivity extends BaseActivity {


    @BindView(R.id.tv_us_version)
    TextView tv_us_version;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @Override
    protected void initData() {

    }

    @Override
    protected boolean isUseFullScreenMode() {
        return  true;
    }

    @Override
    protected void initView() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton( ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("关于我们").setTextColor(Color.parseColor("#EBF9FF"));


        tv_us_version.setText(AppUtils.getAppVersionName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}
