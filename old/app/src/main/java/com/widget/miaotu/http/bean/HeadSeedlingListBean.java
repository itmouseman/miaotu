package com.widget.miaotu.http.bean;

public class HeadSeedlingListBean {
    private String gardenId;//苗圃id，获取指定苗圃下的登记苗木需要传
    private int status; //	苗木的状态，当需要查询特定状态的登记苗木需要传，不传：默认为0,查询所有苗木；1：查询除了不通过审核的所有其他苗木 2：查询不通过的苗木；4：查看别人的登记苗木，显示所有的登记正常的苗木
    private int page;//必传
    private int num;//必传
    private String searchWord; //搜索词
    private String userId;//	用户id，当要查看对方的登记苗木

    public HeadSeedlingListBean(int page, int num) {
        this.page = page;
        this.num = num;
    }

    public HeadSeedlingListBean(String gardenId, int page, int num) {
        this.gardenId = gardenId;
        this.page = page;
        this.num = num;
    }


    public HeadSeedlingListBean(int page, int num, String userId) {
        this.page = page;
        this.num = num;
        this.userId = userId;
    }

    public HeadSeedlingListBean(String gardenId, int status, int page, int num, String searchWord, String userId) {
        this.gardenId = gardenId;
        this.status = status;
        this.page = page;
        this.num = num;
        this.searchWord = searchWord;
        this.userId = userId;
    }
}
