package com.widget.miaotu.http.bean;

public class IdAndPageBean {

    /**
     * id : 134     *
     */

    private int id;
    private int page;
    private int num;


    public IdAndPageBean(int id, int page, int num) {
        this.id = id;
        this.page = page;
        this.num = num;
    }

    public IdAndPageBean(  int page, int num) {
        this.page = page;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
