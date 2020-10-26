package com.widget.miaotu.http.bean;

/**
 * @author tzy
 */
public class MyFollowListBean {

    /**
     * address : 浙江省杭州市余杭区
     * name : 鸬鸟苗圃园林公司
     * logo : 1
     * id : 9
     */

    private String address;
    private String name;
    private String logo;
    private int id;

    @Override
    public String toString() {
        return "MyFollowListBean{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", id=" + id +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
