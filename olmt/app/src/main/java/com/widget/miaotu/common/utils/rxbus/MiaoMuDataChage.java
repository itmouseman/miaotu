package com.widget.miaotu.common.utils.rxbus;

public class MiaoMuDataChage {
    private boolean isChange;

    public MiaoMuDataChage(boolean isChange) {
        this.isChange = isChange;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}
