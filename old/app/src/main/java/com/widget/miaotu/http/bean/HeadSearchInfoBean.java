package com.widget.miaotu.http.bean;

public class HeadSearchInfoBean {
    private String searchWord;
    private String page;
    private String num;

    public HeadSearchInfoBean(String searchWord, String page, String num) {
        this.searchWord = searchWord;
        this.page = page;
        this.num = num;
    }
}
