package com.widget.miaotu.http.bean;

public class IdAndTokenBean {

    /**
     * id : 134
     * token:37498747*
     */

    private String id;

    private String token;


    public IdAndTokenBean(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
