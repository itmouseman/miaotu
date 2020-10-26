package com.widget.miaotu.http.bean;

import android.text.TextUtils;

public class QiuGouHeadsjavaBean {
    private int status;//求购苗木的状态标识，0：查询求购中的；1：查询结束求购的;2：求购大厅页求购列表
    private int page;
    private int num;
    private String searchWord;//	首页搜索时需要传
    private String userId;//用户id，当要查看对方的求购苗木，需要传
    private String province;//求购大厅查询；省
    private String city;//求购大厅查询；市
    private int isVip;//是否是vip（0：不是；1：是）

    public QiuGouHeadsjavaBean(int status, int page, int num, String searchWord, String userId, String province, String city, int isVip) {
        this.status = status;
        this.page = page;
        this.num = num;
        if (!TextUtils.isEmpty(searchWord)) {
            this.searchWord = searchWord;
        }
        this.userId = userId;
        if (!TextUtils.isEmpty(province)) {
            this.province = province;
        }

        if (!TextUtils.isEmpty(city)) {
            this.city = city;
        }
        this.isVip = isVip;
    }
}
