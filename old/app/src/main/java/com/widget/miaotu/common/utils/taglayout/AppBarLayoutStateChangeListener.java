package com.widget.miaotu.common.utils.taglayout;

import com.blankj.utilcode.util.ConvertUtils;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarLayoutStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

   public enum State {
        EXPANDED,//展开
        COLLAPSED,//折叠
        INTERMEDIATE//中间状态
    }

    private State mCurrentState = State.INTERMEDIATE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, verticalOffset);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= (appBarLayout.getTotalScrollRange() - ConvertUtils.dp2px(50))) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, verticalOffset);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.INTERMEDIATE) {
                onStateChanged(appBarLayout, State.INTERMEDIATE, verticalOffset);
            }
            mCurrentState = State.INTERMEDIATE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset);
}
