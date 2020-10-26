package com.widget.miaotu.http.bean;

/**
 * @author tzy
 */
public class GardenListBean {

    /**
     * id : 1
     * gardenName : 1号苗圃
     * sellNum : 100
     * soldOutNum : 100
     */

    private int id;
    private String gardenName;
    private int sellNum;
    private int soldOutNum;

    @Override
    public String toString() {
        return "GardenListBean{" +
                "id=" + id +
                ", gardenName='" + gardenName + '\'' +
                ", sellNum=" + sellNum +
                ", soldOutNum=" + soldOutNum +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public int getSoldOutNum() {
        return soldOutNum;
    }

    public void setSoldOutNum(int soldOutNum) {
        this.soldOutNum = soldOutNum;
    }
}
