package com.widget.miaotu.http.bean;

public class ChangePassWordJavaBean {
    private String phone;
    private String newPassword;
    private String checkCode;


    @Override
    public String toString() {
        return "ChangePassWordJavaBean{" +
                "phone='" + phone + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", checkCode='" + checkCode + '\'' +
                '}';
    }

    public ChangePassWordJavaBean(String phone, String newPassword, String checkCode) {
        this.phone = phone;
        this.newPassword = newPassword;
        this.checkCode = checkCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
