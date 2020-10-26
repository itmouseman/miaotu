package com.widget.miaotu.http.bean;

public class LoginTokenBean {
    private String newToken;
    private String oldToken;

    @Override
    public String toString() {
        return "LoginTokenBean{" +
                "newToken='" + newToken + '\'' +
                ", oldToken='" + oldToken + '\'' +
                '}';
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getOldToken() {
        return oldToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }
}
