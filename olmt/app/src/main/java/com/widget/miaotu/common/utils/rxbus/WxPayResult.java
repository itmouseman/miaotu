package com.widget.miaotu.common.utils.rxbus;

public class WxPayResult {
    private int stateCode;

    public WxPayResult(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }
}
