package com.widget.miaotu.http.bean;

public class SMSLoginSendBeanNew {

    /**
     * mobile : 13454100020
     * code : 0271
     */

    private String phone;
    private String checkCode;

    public SMSLoginSendBeanNew(String phone, String checkCode) {
        this.phone = phone;
        this.checkCode = checkCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
