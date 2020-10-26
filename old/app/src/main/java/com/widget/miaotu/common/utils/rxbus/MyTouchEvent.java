package com.widget.miaotu.common.utils.rxbus;

/**
 * 触摸事件分发消息
 */
public class MyTouchEvent {
    public boolean needTouch;

    public MyTouchEvent(boolean needTouch) {
        this.needTouch = needTouch;
    }

    public boolean isNeedTouch() {
        return needTouch;
    }

    public void setNeedTouch(boolean needTouch) {
        this.needTouch = needTouch;
    }
}
