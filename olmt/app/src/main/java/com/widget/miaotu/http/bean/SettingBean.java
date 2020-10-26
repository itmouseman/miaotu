package com.widget.miaotu.http.bean;

public class SettingBean {
    private String title;
    private Object icon;


    public SettingBean(String title, Object icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }
}
