package com.widget.miaotu.http.bean;

public class PhoneLoginSendNewBean {

    /**
     * password : 14e1b600b1fd579f47433b88e8d85291
     * phone : 13454100020
     */

    private String password;
    private String phone;

    public PhoneLoginSendNewBean(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
