package com.widget.miaotu.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.other.ActivityStackManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Activity
 */
public abstract class BaseAbsActivity extends AppCompatActivity {

    private Unbinder bind;
    //页面的堆栈管理
    private ActivityStackManager mStackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mStackManager = ActivityStackManager.getInstance();
        mStackManager.pushOneActivity(this);
        //做一些初始化操作
        bind = ButterKnife.bind(this);
        //初始化界面
        initCView();
        //请求数据
        initCData();


        changeStatusWhite(false);
    }

    protected void changeStatusWhite(boolean change) {
        if (change) {
            int mode = StatusBarUtil.StatusBarLightMode(this);
            if (mode == 0) {
                StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorAccent));
            } else {
                StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.white_color));
                StatusBarUtil.StatusBarLightMode(this, mode);
            }
        }
    }

    protected void changeStatusWhite(boolean change, int color) {
        if (change) {
            StatusBarUtil.setStatusBarColor(this, getResources().getColor(color));
        }
    }


    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(nonRoot);
    }

    protected abstract void initCData();

    protected abstract void initCView();


    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        if (bind != Unbinder.EMPTY) {
            bind.unbind();
        }
        mStackManager.popOneActivity(this);
        super.onDestroy();
    }
}
