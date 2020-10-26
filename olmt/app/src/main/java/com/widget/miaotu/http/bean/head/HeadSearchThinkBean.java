package com.widget.miaotu.http.bean.head;

public class HeadSearchThinkBean {
    private String word;
    private String type;
    private int pageNum;
    private int pageSize;

    public HeadSearchThinkBean(String word, String type, int pageNum, int pageSize) {
        this.word = word;
        this.type = type;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
