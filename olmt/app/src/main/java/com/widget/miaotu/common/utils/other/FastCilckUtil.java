package com.widget.miaotu.common.utils.other;

/**
 * 重复点击
 */
public class FastCilckUtil {
    // 两次点击按钮之间的点击间隔不能少于1500毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return !flag;
    }
}
