package com.widget.miaotu.common.utils.rxbus;

public class HomeUpdateChange {
    private boolean isChange;


    public HomeUpdateChange(boolean isChange) {
        this.isChange = isChange;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}
