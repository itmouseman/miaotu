package com.widget.miaotu.http.bean;

public class ImgUrlJavaBean {

    private String t_height;
    private String t_url;
    private String t_width;

    @Override
    public String toString() {
        return "TuiGuangImgUrlJavaBeanDetail{" +
                "t_height='" + t_height + '\'' +
                ", t_url='" + t_url + '\'' +
                ", t_width='" + t_width + '\'' +
                '}';
    }

    public String getT_height() {
        return t_height;
    }

    public void setT_height(String t_height) {
        this.t_height = t_height;
    }

    public String getT_url() {
        return t_url;
    }

    public void setT_url(String t_url) {
        this.t_url = t_url;
    }

    public String getT_width() {
        return t_width;
    }

    public void setT_width(String t_width) {
        this.t_width = t_width;
    }
}