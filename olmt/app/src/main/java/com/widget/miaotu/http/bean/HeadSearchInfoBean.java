package com.widget.miaotu.http.bean;

public class HeadSearchInfoBean {
    private String searchWord;
    private int page;
    private int num;

    public HeadSearchInfoBean(String searchWord, int page, int num) {
        this.searchWord = searchWord;
        this.page = page;
        this.num = num;
    }
}
