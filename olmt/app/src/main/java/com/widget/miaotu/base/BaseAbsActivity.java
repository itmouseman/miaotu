package com.widget.miaotu.base;

import android.os.Build;
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
        setStatusBar();

    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor());
            }

            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode());
            }
        }
    }
    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected boolean isUseFullScreenMode() {
        return false;
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    protected int getStatusBarColor() {
        return R.color.white_color;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
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
