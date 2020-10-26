package com.widget.miaotu.http.bean;

public class AuthSubmitBean {

    private String cardId;

    private String realname;

    public AuthSubmitBean(String realname, String cardId) {
        this.realname = realname;
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
