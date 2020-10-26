package com.widget.miaotu.common;

import android.view.KeyEvent;
import android.view.View;

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

        qdWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && qdWebView.canGoBack()) {
                        //后退
                        qdWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        qdWebView.loadUrl(webUrl);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_web_view;
    }
}