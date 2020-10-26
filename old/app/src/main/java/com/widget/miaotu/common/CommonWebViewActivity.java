package com.widget.miaotu.common;

import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.QDWebView;

import butterknife.BindView;

public class CommonWebViewActivity extends BaseActivity {

    @BindView(R.id.common_Webview)
    QDWebView qdWebView;
    private String webUrl;
    private String webTitle;



    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        webUrl = getIntent().getStringExtra(SPConstant.WEB_URL);
        webTitle = getIntent().getStringExtra(SPConstant.WEB_TITLE);
        qdWebView.loadUrl(webUrl);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_web_view;
    }
}