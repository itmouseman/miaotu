package com.widget.miaotu.http.bean;

public class SendSMSSendBeanNew {
    private String phone;
    private int sendType;

    public SendSMSSendBeanNew(String phone, int sendType) {
        this.phone = phone;
        this.sendType = sendType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
