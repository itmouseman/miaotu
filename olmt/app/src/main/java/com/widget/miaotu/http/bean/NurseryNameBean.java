package com.widget.miaotu.http.bean;

public class NurseryNameBean {
    private String title;
    private String id;
    private int  isShow;
    public NurseryNameBean(String title, String id, int isShow) {
        this.title = title;
        this.id = id;
        this.isShow = isShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}
