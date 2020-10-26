package com.widget.miaotu.http.bean;

public class BindMobileJavaBeanTwo {

    private String phone;
    private String  wxId;

    private String checkCode;

    public BindMobileJavaBeanTwo(String phone, String wxId, String checkCode) {
        this.phone = phone;
        this.wxId = wxId;
        this.checkCode = checkCode;
    }
}
