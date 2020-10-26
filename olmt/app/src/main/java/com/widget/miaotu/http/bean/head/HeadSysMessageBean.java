package com.widget.miaotu.http.bean.head;

public class HeadSysMessageBean {
    private int pageSize;
    private int pageNum;

    public HeadSysMessageBean(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }
}
