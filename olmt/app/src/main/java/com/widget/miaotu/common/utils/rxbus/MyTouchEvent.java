package com.widget.miaotu.common.utils.rxbus;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * 触摸事件分发消息
 */
public class MyTouchEvent {
    public   int mTypeLoad;
    public   SmartRefreshLayout mSrlUserInfo;
    public   int tabPosition;

    public MyTouchEvent(int type,int tabPosition, SmartRefreshLayout srlUserInfo) {

        this.tabPosition = tabPosition;
        mSrlUserInfo =srlUserInfo;
        mTypeLoad = type;
    }

    public int getmTypeLoad() {
        return mTypeLoad;
    }

    public void setmTypeLoad(int mTypeLoad) {
        this.mTypeLoad = mTypeLoad;
    }

    public SmartRefreshLayout getmSrlUserInfo() {
        return mSrlUserInfo;
    }

    public void setmSrlUserInfo(SmartRefreshLayout mSrlUserInfo) {
        this.mSrlUserInfo = mSrlUserInfo;
    }

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
