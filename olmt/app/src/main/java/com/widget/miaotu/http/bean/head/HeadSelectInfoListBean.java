package com.widget.miaotu.http.bean.head;

/**
 * author : heyang
 * e-mail :
 * date   : 2020/8/1711:31
 * desc   :
 * version: 1.0
 */
public class HeadSelectInfoListBean {
    private String page;
    private String num;
    private String programId;

    public HeadSelectInfoListBean(String page, String num, String programId) {
        this.page = page;
        this.num = num;
        this.programId = programId;
    }
}
